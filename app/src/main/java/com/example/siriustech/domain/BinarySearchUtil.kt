package com.example.siriustech.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * [isOutOfRange] is used for checking that index not found in the search list
 */
fun Pair<Int, Int>.isOutOfRange() = first == -1 && second == -1


object BinarySearchUtil {

    /**
     * [findPageRange] is used to find the range for [currentPage]
     * @param currentPage is the current page.
     * @param pageSize is the size of page that we need to fetch each time.
     * @param range is the value that indicate the start and end length of the [BinarySearchModel] list.
     */
    fun findPageRange(
        currentPage: Int,
        pageSize: Int,
        range: Pair<Int, Int>
    ): Pair<Int, Int> {
        val firstIndex = range.first
        val lastIndex = range.second

        val firstIndexInCurrentPage = firstIndex + (currentPage - 1) * pageSize

        val lastIndexInCurrentPage = when {
            firstIndexInCurrentPage > lastIndex -> lastIndex
            firstIndexInCurrentPage + pageSize > lastIndex -> lastIndex
            else -> firstIndexInCurrentPage + pageSize
        }

        return firstIndexInCurrentPage to lastIndexInCurrentPage
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
                binarySearches = binarySearches,
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
     * This should take time not more than ≈ Log(n)
     *
     * @param binarySearches should be a model that extend from BinarySearch
     * @param char should be a char use for search
     * @param startIndex is a start index that the algorithm will start searching for.
     * @param lastIndex is an end index that the algorithm will search for.
     * @param currentComparisonIndex is an index of char that we are interested in.
     * It will be taken from string and iterate each of them
     *
     * @return It will provide rage of min and max index from binary search for example
     * ['a','b','c','c','c','d','e'] the result of this array should provide (2,4).
     * If [char] is equal 'c'
     */
    private suspend fun <T : BinarySearchModel> binarySearchRange(
        binarySearches: List<T>,
        char: Char,
        startIndex: Int,
        lastIndex: Int,
        currentComparisonIndex: Int
    ): Pair<Int, Int> = withContext(Dispatchers.Default) {

        //region iterate first time to find first match.
        val firstMatch: Int = binarySearch(
            binarySearches, startIndex, lastIndex, char.code, currentComparisonIndex
        )

        if (firstMatch == -1) return@withContext -1 to -1

        var leftMost = firstMatch
        var rightMost = firstMatch
        //endregion

        //region search for the leftmost ≈ Log(n)
        var result: Int = binarySearch(
            binarySearches = binarySearches,
            startIndex = startIndex,
            lastIndex = leftMost - 1,
            expectedChar = char.code,
            indexComparison = currentComparisonIndex
        )
        while (result != -1) {
            leftMost = result
            result = binarySearch(
                binarySearches = binarySearches,
                startIndex = startIndex,
                lastIndex = leftMost - 1,
                expectedChar = char.code,
                indexComparison = currentComparisonIndex
            )
        }
        //endregion

        //region search for rightmost ≈ Log(n)
        result = binarySearch(
            binarySearches = binarySearches,
            startIndex = rightMost + 1,
            lastIndex = lastIndex,
            expectedChar = char.code,
            indexComparison = currentComparisonIndex
        )
        while (result != -1) {
            rightMost = result
            result = binarySearch(
                binarySearches = binarySearches,
                startIndex = rightMost + 1,
                lastIndex = lastIndex,
                expectedChar = char.code,
                indexComparison = currentComparisonIndex
            )
        }
        //endregion

        leftMost to rightMost
    }


    /**
     * [binarySearch] is designed to search [expectedChar] within [binarySearches] list.
     *
     * @param startIndex is a start index that the algorithm will start searching for.
     * @param lastIndex is an end index that the algorithm will search for.
     * @param expectedChar is the ASCII code that use to compare.
     * @param indexComparison is the index of string within [binarySearches] list.
     *
     * @return This should provide the match index of [binarySearches] list.
     */
    private suspend fun <T : BinarySearchModel> binarySearch(
        binarySearches: List<T>,
        startIndex: Int,
        lastIndex: Int,
        expectedChar: Int,
        indexComparison: Int
    ): Int = withContext(Dispatchers.Default) {
        if (startIndex > lastIndex) return@withContext -1

        // ship bit to right side or we can divide by 2
        val mid = startIndex + (lastIndex - startIndex).ushr(1)

        if (mid == -1) return@withContext -1

        val charAtPosition = when (indexComparison > binarySearches[mid].searchName.lastIndex) {
            true -> ' '.code
            else -> binarySearches[mid].searchName[indexComparison].code
        }

        when {
            expectedChar == charAtPosition -> mid
            expectedChar < charAtPosition -> binarySearch(
                binarySearches,
                startIndex,
                mid - 1,
                expectedChar,
                indexComparison
            )
            else -> binarySearch(binarySearches, mid + 1, lastIndex, expectedChar, indexComparison)
        }
    }
}