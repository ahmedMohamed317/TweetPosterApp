package com.example.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSavedCityUseCaseTest {

    private lateinit var repo: CityRepository
    private lateinit var getSavedCityUseCase: GetSavedCityUseCase

    @Before
    fun setup() {
        repo = mockk()
        getSavedCityUseCase = GetSavedCityUseCase(repo)
    }

    @Test
    fun `invoke() should return saved city list from repository`() = runBlocking {
        // Given
        val expectedCityList = listOf(
            City("City1", "Country1", "1"),
            City("City2", "Country2", "2")
        )

        // Mock repository behavior
        coEvery { repo.getAllCities() } returns expectedCityList

        // When
        val result = getSavedCityUseCase()

        // Then
        assertEquals(expectedCityList, result)
    }
}
