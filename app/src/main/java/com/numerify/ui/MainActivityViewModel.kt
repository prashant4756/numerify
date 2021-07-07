package com.numerify.ui

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel


class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    // Storing data into SharedPreferences
    var sharedPreferences: SharedPreferences = application.getSharedPreferences("Numerify", MODE_PRIVATE)

    // Creating an Editor object to edit(write to the file)
    var preferenceEditor = sharedPreferences.edit()

    var map : MutableMap<Char, Int> = mutableMapOf()

    fun setUpDefault() {
        map.clear()
        map.putAll(getDefaultNumeral())
    }

    private fun getDefaultNumeral(): Map<Char, Int> {
        return mapOf(
                'A' to 1,
                'B' to 2,
                'C' to 3,
                'D' to 4,
                'E' to 5,
                'F' to 8,
                'G' to 3,
                'H' to 5,
                'I' to 1,
                'J' to 1,
                'K' to 2,
                'L' to 3,
                'M' to 4,
                'N' to 5,
                'O' to 7,
                'P' to 8,
                'Q' to 1,
                'R' to 2,
                'S' to 3,
                'T' to 4,
                'U' to 6,
                'V' to 6,
                'W' to 6,
                'X' to 5,
                'Y' to 1,
                'Z' to 7
        )
    }

}