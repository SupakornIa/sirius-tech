package com.example.siriustech.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * [isOutOfRange] is used for checking that index not found in the search list
 */
fun Pair<Int, Int>.isOutOfRange() = first == -1 && second == -1


object BinarySearchUtil {

    fun findPageRange(
        currentPage: Int,
        pageSize: Int,
        range: Pair<Int, Int>
    ): Pair<Int, Int> {
        val firstIndex = range.first
        val lastIndex = range.second

        val firstMapIndex = firstIndex + (currentPage - 1) * pageSize

        val lastFoundIndex = when {
            firstMapIndex > lastIndex -> lastIndex
            firstMapIndex + pageSize > lastIndex -> lastIndex
            else -> firstMapIndex + pageSize
        }

        return firstMapIndex to lastFoundIndex
    }

    /**
     * [findRange] before we use this method. We need to make sure that [binarySearches] are sorted.
     * @return It will provide a pair of range for example (0,100) 0 is first index found
     * and 100 is the last index found. (-1,-1) this means. it has not found text in the list.
     */
    suspend fun <T : BinarySearchModel> findRange(
        query: String,
        binarySearches: List<T>
    ): Pair<Int, Int> {
        var startIndex = 0
        var endIndex = binarySearches.lastIndex

        query.lowercase().forEachIndexed { index, char ->
            val range = binarySearchRange(
                list = binarySearches,
                char = char,
                startIndex = startIndex,
                lastIndex = endIndex,
                currentComparisonIndex = index
            )
            startIndex = range.first
            endIndex = range.second
        }

        return startIndex to endIndex
    }

    /**
     * This should take time not more than 2 Log(n) ≈ Log(n)
     *
     * @param list should be a model that extend from BinarySearch
     * @param char should be a char use for search
     * @param startIndex is a start index that the algorithm will search for.
     * @param lastIndex is an end index that the algorithm will search for.
     * @param currentComparisonIndex is an index of char that we are interested in.
     *
     * @return It will provide rage of min and max index from binary search for example
     * ['a','b','c','c','c','d','e'] the result of this array should provide (2,4).
     * If [char] is equal 'c'
     */
    private suspend fun <T : BinarySearchModel> binarySearchRange(
        list: List<T>,
        char: Char,
        startIndex: Int,
        lastIndex: Int,
        currentComparisonIndex: Int
    ): Pair<Int, Int> = withContext(Dispatchers.Default) {
        val firstMatch: Int = binarySearch(
            list, startIndex, lastIndex, char.code, currentComparisonIndex
        )

        if (firstMatch == -1) return@withContext -1 to -1

        var leftMost = firstMatch
        var rightMost = firstMatch


        // search to find leftmost log(n)
        var result: Int = binarySearch(
            list = list,
            start = startIndex,
            end = leftMost - 1,
            expectedChar = char.code,
            indexComparison = currentComparisonIndex
        )
        while (result != -1) {
            leftMost = result
            result = binarySearch(
                list = list,
                start = startIndex,
                end = leftMost - 1,
                expectedChar = char.code,
                indexComparison = currentComparisonIndex
            )
        }

        // search to rightmost log(n)
        result = binarySearch(
            list = list,
            start = rightMost + 1,
            end = lastIndex,
            expectedChar = char.code,
            indexComparison = currentComparisonIndex
        )
        while (result != -1) {
            rightMost = result
            result = binarySearch(
                list = list,
                start = rightMost + 1,
                end = lastIndex,
                expectedChar = char.code,
                indexComparison = currentComparisonIndex
            )
        }

        leftMost to rightMost
    }


    private suspend fun <T : BinarySearchModel> binarySearch(
        list: List<T>,
        start: Int,
        end: Int,
        expectedChar: Int,
        indexComparison: Int
    ): Int = withContext(Dispatchers.Default) {
        if (start > end) return@withContext -1

        // ship bit to right side or we can divide by 2
        val mid = start + (end - start).ushr(1)

        if (mid == -1) return@withContext -1

        val charAtPosition = when (indexComparison > list[mid].searchName.lastIndex) {
            true -> ' '.code
            else -> list[mid].searchName[indexComparison].code
        }

        when {
            expectedChar == charAtPosition -> mid
            expectedChar < charAtPosition -> binarySearch(
                list,
                start,
                mid - 1,
                expectedChar,
                indexComparison
            )
            else -> binarySearch(list, mid + 1, end, expectedChar, indexComparison)
        }
    }
}