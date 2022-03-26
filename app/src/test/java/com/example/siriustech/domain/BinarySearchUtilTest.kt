package com.example.siriustech.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class BinarySearchUtilTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Test
    fun `mockup for testing should be correct in every index after sorting`() {
        SearchMockUp.mockup.sortedBy(SearchModel::searchName)
            .forEachIndexed { index, searchModel ->
                Assert.assertTrue(
                    "mockup index: $index should match to mock up",
                    searchModel.searchName == SearchMockUp.mockup[index].searchName
                )
            }
    }

    @Test
    fun `The range should be correct after search in each case`() {
        runBlocking(testCoroutineDispatcher) {
            val pairs = listOf(
                BinarySearchUtil.findRange(
                    query = "a",
                    binarySearches = SearchMockUp.mockup
                ) to (0 to 9),

                BinarySearchUtil.findRange(
                    query = "aA",
                    binarySearches = SearchMockUp.mockup
                ) to (0 to 3),

                BinarySearchUtil.findRange(
                    query = "ab",
                    binarySearches = SearchMockUp.mockup
                ) to (4 to 4),

                BinarySearchUtil.findRange(
                    query = "aba",
                    binarySearches = SearchMockUp.mockup
                ) to (4 to 4),

                BinarySearchUtil.findRange(
                    query = "acac",
                    binarySearches = SearchMockUp.mockup
                ) to (5 to 6),

                BinarySearchUtil.findRange(
                    query = "Caabiang",
                    binarySearches = SearchMockUp.mockup
                ) to (22 to 22),

                )
            pairs.forEachIndexed { index, (result, expectedResult) ->
                Assert.assertTrue(
                    "index: $index, (${result.first}, ${result.second}) should match to (${expectedResult.first}, ${expectedResult.second})",
                    result.first == expectedResult.first && result.second == expectedResult.second
                )
            }

        }
    }

    @Test
    fun `The range should not be found in case no query match`() {
        runBlocking(testCoroutineDispatcher) {
            val pairs = listOf(
                BinarySearchUtil.findRange(
                    query = "caabiangana",
                    binarySearches = SearchMockUp.mockup
                ),

                BinarySearchUtil.findRange(
                    query = "z",
                    binarySearches = SearchMockUp.mockup
                ),

                BinarySearchUtil.findRange(
                    query = "aG",
                    binarySearches = SearchMockUp.mockup
                ),
            )

            pairs.forEach { result ->
                Assert.assertTrue(
                    "result should out of range (-1,-1), (${result.first},${result.second})",
                    result.isOutOfRange()
                )
            }
        }
    }

    @Test
    fun `find page range should be correct in all case`() {
        runBlocking(testCoroutineDispatcher) {
            val pairs = listOf(
                BinarySearchUtil.findPageRange(
                    currentPage = 1,
                    pageSize = 5,
                    range = 0 to 16
                ) to (0 to 4),
                BinarySearchUtil.findPageRange(
                    currentPage = 2,
                    pageSize = 5,
                    range = 0 to 16
                ) to (5 to 9),
                BinarySearchUtil.findPageRange(
                    currentPage = 3,
                    pageSize = 5,
                    range = 0 to 16
                ) to (10 to 14),
                BinarySearchUtil.findPageRange(
                    currentPage = 4,
                    pageSize = 5,
                    range = 0 to 16
                ) to (15 to 16),

                BinarySearchUtil.findPageRange(
                    currentPage = 1,
                    pageSize = 5,
                    range = 0 to 0
                ) to (0 to 0),
                BinarySearchUtil.findPageRange(
                    currentPage = 1,
                    pageSize = 5,
                    range = -1 to -1
                ) to (-1 to -1),
                BinarySearchUtil.findPageRange(
                    currentPage = 2,
                    pageSize = 5,
                    range = -1 to -1
                ) to (-1 to -1)
            )

            pairs.toMutableList().forEach { (result, expectedResult) ->
                Assert.assertTrue(
                    "result should be equal between ($result) and ($expectedResult)",
                    result.first == expectedResult.first && result.second == expectedResult.second
                )
            }
        }
    }
}