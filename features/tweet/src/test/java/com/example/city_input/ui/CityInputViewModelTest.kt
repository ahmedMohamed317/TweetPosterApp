package com.example.city_input.ui

import com.example.core.util.EmptyResponseException
import com.example.core.util.NetworkErrorException
import com.example.core.util.ServerErrorException
import com.example.core.util.UnknownErrorException
import com.example.city_input.util.MainCoroutineRule
import com.example.city_input.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CityInputViewModelTest {

    private lateinit var viewModel: CityInputViewModel
    private lateinit var searchCityByNameUseCase: SearchCityByNameUseCase
    private lateinit var getSavedCityUseCase: GetSavedCityUseCase
    private lateinit var saveCityUseCase: SaveCityUseCase
    private lateinit var testDispatcher: TestDispatchers

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        searchCityByNameUseCase = mockk()
        getSavedCityUseCase = mockk()
        saveCityUseCase = mockk()
        testDispatcher = TestDispatchers()

        viewModel = CityInputViewModel(
            searchCityByNameUseCase,
            getSavedCityUseCase,
            saveCityUseCase,
            testDispatcher
        )
    }

    @Test
    fun `getAllCitiesFromDb() when successful call, then should update state with city list`() =
        runTest {
            // Given
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )

            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList

            // When
            viewModel.getAllCitiesFromDb()
            delay(100)

            // Check final state
            assertEquals(cityList.map { it.toUiModel() }, viewModel.state.value.cityList)
            assertFalse(viewModel.state.value.isLoading)
            assertFalse(viewModel.state.value.isError)
        }
    @Test
    fun `getAllCitiesFromDb() when use case call throws EmptyResponseException, then should update state with empty city list`() =
        runTest {
            // Mock use case call that throws EmptyResponseException
            coEvery { getSavedCityUseCase() } throws EmptyResponseException()

            // When
            viewModel.getAllCitiesFromDb()
            delay(100)
            // Check final state
            assertTrue(viewModel.state.value.cityList.isEmpty())
        }
    @Test
    fun `searchCityByName() when successful call, then should update state with city list`() =
        runTest {
            // Given
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )
            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList
            // Mock successful use case call
            coEvery { searchCityByNameUseCase(any()) } returns cityList

            // When
            viewModel.onSearchTextChange("City")
            delay(900)

            // Check final state
            assertEquals(cityList.map { it.toUiModel() }, viewModel.state.value.cityList)
            assertFalse(viewModel.state.value.isLoading)
            assertFalse(viewModel.state.value.isError)
        }
    @Test
    fun `searchCityByName() when use case call throws EmptyResponseException, then should update state with empty city list`() =
        runTest {
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )
            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList
            // Mock use case call that throws EmptyResponseException
            coEvery { searchCityByNameUseCase(any()) } throws EmptyResponseException()

            // When
            viewModel.onSearchTextChange("City")
            delay(900)

            // Check final state
            assertTrue(viewModel.state.value.isError)
        }
    @Test
    fun `searchCityByName() when use case call throws NetworkErrorException, then should update state with error`() =
        runTest {
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )
            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList
            // Mock use case call that throws NetworkErrorException
            coEvery { searchCityByNameUseCase(any()) } throws NetworkErrorException("Network Error")

            // When
            viewModel.onSearchTextChange("City")
            delay(900)

            // Check final state
            assertTrue(viewModel.state.value.isError)
            assertFalse(viewModel.state.value.isLoading)
        }
    @Test
    fun `searchCityByName() when use case call throws ServerErrorException, then should update state with error`() =
        runTest {
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )
            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList
            // Mock use case call that throws ServerErrorException
            coEvery { searchCityByNameUseCase(any()) } throws ServerErrorException("Server Error")

            // When
            viewModel.onSearchTextChange("City")
            delay(900)

            // Check final state
            assertTrue(viewModel.state.value.isError)
            assertFalse(viewModel.state.value.isLoading)
        }
    @Test
    fun `searchCityByName() when use case call throws UnknownErrorException, then should update state with error`() =
        runTest {
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )
            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList
            // Mock use case call that throws UnknownErrorException
            coEvery { searchCityByNameUseCase(any()) } throws UnknownErrorException("Unknown Error")

            // When
            viewModel.onSearchTextChange("City")
            delay(900)

            // Check final state
            assertTrue(viewModel.state.value.isError)
            assertFalse(viewModel.state.value.isLoading)
        }
    @Test
    fun `saveCity() when successful call, then should update state with success`() =
        runTest {
            // Given
            val city = City(city = "City1", country = "Country1", locationKey = "1")
            val cityList = listOf(
                City(city = "City1", country = "Country1", locationKey = "1"),
                City(city = "City2", country = "Country2", locationKey = "2")
            )
            // Mock successful use case call
            coEvery { getSavedCityUseCase() } returns cityList
            // Mock successful use case call
            coEvery { saveCityUseCase(any()) } returns Unit

            // When
            viewModel.onCitySelected(city.toUiModel())
            delay(100)

            // Check final state
            coVerify { saveCityUseCase(city) }
        }



}
