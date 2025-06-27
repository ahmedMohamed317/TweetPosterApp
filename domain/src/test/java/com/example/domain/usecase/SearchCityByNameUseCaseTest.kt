package com.example.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchCityByNameUseCaseTest {

    private lateinit var repo: CityRepository
    private lateinit var searchCityByNameUseCase: SearchCityByNameUseCase

    @Before
    fun setup() {
        repo = mockk()
        searchCityByNameUseCase = SearchCityByNameUseCase(repo)
    }

    @Test
    fun `invoke() should return a list of cities based on the search query`() = runTest {
        // Given
        val searchQuery = "TestCity"
        val expectedCityList = listOf(
            City("TestCity1", "TestCountry1", "1"),
            City("TestCity2", "TestCountry2", "2")
        )

        // Mock repository behavior
        coEvery { repo.searchCityByName(searchQuery) } returns expectedCityList

        // When
        val result = searchCityByNameUseCase(searchQuery)

        // Then
        Assert.assertEquals(expectedCityList, result)
    }
}
