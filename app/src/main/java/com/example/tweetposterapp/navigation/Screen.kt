package com.example.tweetposterapp.navigation

import com.example.tweetposterapp.navigation.Constants.Companion.CITY_SCREEN
import com.example.tweetposterapp.navigation.Constants.Companion.CURRENT_WEATHER_SCREEN
import com.example.tweetposterapp.navigation.Constants.Companion.FORECAST_WEATHER_SCREEN
import com.example.tweetposterapp.navigation.Constants.Companion.LOCATION_KEY

sealed class Screen(val route: String) {
    object CityInput : Screen(route = CITY_SCREEN)
    object CurrentWeather : Screen(route="${CURRENT_WEATHER_SCREEN}/{$LOCATION_KEY}"){
        fun passedLocationKey(locationKey: String): String {
            return "$CURRENT_WEATHER_SCREEN/${locationKey}"
        }
    }
    object ForecastWeather : Screen(route="${FORECAST_WEATHER_SCREEN}/{$LOCATION_KEY}"){
        fun passedLocationKey(locationKey: String): String {
            return "$FORECAST_WEATHER_SCREEN/${locationKey}"
        }
    }
}
class Constants {
    companion object{
        const val CITY_SCREEN = "city_input"
        const val CURRENT_WEATHER_SCREEN = "current_weather"
        const val FORECAST_WEATHER_SCREEN = "forecast_weather"
        const val LOCATION_KEY = "location"
    }
}
class Solution {
    fun isValid(s: String): Boolean {
        if (s.length % 2 != 0) return false

        val stack = ArrayDeque<Char>()
        for (char in s) {
            println("char "+char)
            when (char) {
                '(', '{', '[' -> stack.addFirst(char)
                ')' -> if (stack.isEmpty() || stack.removeFirst() != '(') return false
                '}' -> if (stack.isEmpty() || stack.removeFirst() != '{') return false
                ']' -> if (stack.isEmpty() || stack.removeFirst() != '[') return false
            }
            println("stack "+stack.toString())

        }

        return stack.isEmpty() // Valid if the stack is empty
    }
}

fun main(){
    val solution = Solution()
    println(solution.isValid("{[]}"))      // true
}

