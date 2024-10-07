package com.example.githubclient.feature_app.data.local.type_converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class BaseTypeConverter<T>(private val typeToken: TypeToken<T>) {

    private val gson = Gson()

    @TypeConverter
    fun toJson(value: T): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): T {
        // Note: This uses the fromJson(String, TypeToken) overload added in Gson 2.10
        return gson.fromJson(json, typeToken)
    }




}

