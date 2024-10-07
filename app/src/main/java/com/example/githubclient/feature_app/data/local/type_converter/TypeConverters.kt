package com.example.githubclient.feature_app.data.local.type_converter

import androidx.room.TypeConverter
import com.example.githubclient.feature_app.data.remote.response.github_repository.License
import com.example.githubclient.feature_app.data.remote.response.github_repository.Owner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OwnerTypeConverter : BaseTypeConverter<Owner>(TypeToken.get(Owner::class.java))
class LicenseTypeConverter :
    BaseTypeConverter<License>(TypeToken.get(License::class.java))

class StringListTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(list: List<String>): String {
        return gson.toJson(list)
    }
}