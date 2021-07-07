package com.numerify.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.numerify.preference.SharedPrefUtil
import java.math.BigInteger


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var sumMutableLiveData: MutableLiveData<BigInteger> = MutableLiveData()
    val sumLiveData: LiveData<BigInteger> = sumMutableLiveData

    private var squareMutableLiveData: MutableLiveData<BigInteger> = MutableLiveData()
    val squareLiveData: LiveData<BigInteger> = squareMutableLiveData

    var defaultMap: MutableMap<Char, Int> = mutableMapOf()

    fun setUpDefault() {
        defaultMap.putAll(getDefaultNumeral())
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

    fun convertToNumerals(inputText: CharSequence?) {
        if (inputText.isNullOrEmpty()) {
            sumMutableLiveData.postValue(BigInteger.ZERO)
            squareMutableLiveData.postValue(BigInteger.ZERO)
            return
        }

        val numeralsMap = getPrefNumeralsAsMap()
        var sum: BigInteger = BigInteger.ZERO
        var square: BigInteger = BigInteger.ZERO
        inputText.forEach {
            val character = it.toUpperCase()
            val numeralValueForChar = numeralsMap[character]
            sum = sum.add(BigInteger.valueOf((numeralValueForChar?.toLong() ?: 0L)))
            sumMutableLiveData.postValue(sum)
        }
        square = sum * sum
        squareMutableLiveData.postValue(square)
    }

    private fun getPrefNumeralsAsMap(): Map<Char, Int> {
        return SharedPrefUtil.getPrefNumeralsAsMap(getApplication(), getDefaultNumeral())
    }

}