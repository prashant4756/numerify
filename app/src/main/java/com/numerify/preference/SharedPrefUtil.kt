package com.numerify.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.numerify.model.EditConfModel
import org.json.JSONObject

object SharedPrefUtil {

    const val PREF_NAME = "Numerify"
    const val NUMERALS_MAP_PREF = "numerals_map_in_pref"

    // Storing data into SharedPreferences
    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    private fun updatePreferenceIfEmpty(context: Context, defaultMap: Map<Char, Int>) {
        val sharedPreferences = getPreference(context)
        val defaultJson = Gson().toJson(defaultMap)
        val prefValue = sharedPreferences.getString(NUMERALS_MAP_PREF, null)
        if (prefValue.isNullOrEmpty() || prefValue.equals("{}"))
            sharedPreferences.edit().putString(NUMERALS_MAP_PREF, defaultJson).apply()
    }

    fun getPrefNumeralsAsMap(context: Context, defaultMap: Map<Char, Int>): Map<Char, Int> {
        updatePreferenceIfEmpty(context, defaultMap)
        return getPrefAsMap(context)
    }

    private fun getPrefAsMap(context: Context): MutableMap<Char, Int> {
        val sharedPreferences = getPreference(context)
        val jsonString: String =
                sharedPreferences.getString(NUMERALS_MAP_PREF, JSONObject().toString()).orEmpty()
        val dataType = object : TypeToken<MutableMap<Char, Int>>() {}.type
        return Gson().fromJson(jsonString, dataType)
    }

    fun updatePref(context: Context, model: EditConfModel, newVal: CharSequence?) {
        if(newVal.isNullOrEmpty()) return
        val sharedPreferences = getPreference(context)
        val currentMap = getPrefAsMap(context)
        currentMap[model.key] = newVal.toString().toInt()
        val updatedJson = Gson().toJson(currentMap)
        sharedPreferences.edit().putString(NUMERALS_MAP_PREF, updatedJson).apply()
    }

    fun resetPrefWithDefault(context: Context, defaultMap: Map<Char, Int>) {
        val sharedPreferences = getPreference(context)
        val defaultJson = Gson().toJson(defaultMap)
        sharedPreferences.edit().putString(NUMERALS_MAP_PREF, defaultJson).apply()
    }


}