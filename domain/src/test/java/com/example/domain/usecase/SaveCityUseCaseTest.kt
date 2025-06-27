package com.example.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveCityUseCaseTest {

    private lateinit var repo: CityRepository
    private lateinit var saveCityUseCase: SaveCityUseCase

    @Before
    fun setup() {
        repo = mockk()
        saveCityUseCase = SaveCityUseCase(repo)
    }

    @Test
    fun `invoke() should save the city in the repository`() = runTest {
        // Given
        val cityToSave = City("City1", "Country1", "1")

        // Mock repository behavior
        coEvery { repo.saveCity(cityToSave) } returns Unit

        // When
        saveCityUseCase(cityToSave)

        // Then
        coVerify { repo.saveCity(cityToSave) }
    }

}
