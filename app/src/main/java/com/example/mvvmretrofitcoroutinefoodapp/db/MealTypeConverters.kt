package com.example.mvvmretrofitcoroutinefoodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverters {
    @TypeConverter
    fun fromAnyToString(attr: Any?): String {
        if (attr == null)
            return ""
        return attr.toString()
    }

    @TypeConverter
    fun fromStringToAny(attr : String?) : Any{
        if (attr == null)
            return ""
        return attr
    }

}