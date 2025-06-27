package com.example.data.source.local

import org.junit.Assert.*

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CityDataBaseTest {

    private lateinit var cityDao: CityDao
    private lateinit var cityDatabase: CityDataBase

    @Before
    fun setup() {
        cityDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CityDataBase::class.java
        ).allowMainThreadQueries().build()

        cityDao = cityDatabase.cityDao
    }

    @After
    fun teardown() {
        cityDatabase.close()
    }

    @Test
    fun insertAndRetrieveCities() = runBlocking {
        // Given
        val cities = listOf(
            CityEntity("1", "City1", "Country1"),
            CityEntity("2", "City2", "Country2")
        )

        // When
        cityDao.insertCity(cities[0])
        cityDao.insertCity(cities[1])

        // Then
        val retrievedCities = cityDao.getCities()
        assertEquals(cities, retrievedCities)
    }
}
