package ru.subbotind.android.academy.myfirstapp.data.entity

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre

class Converters {
    companion object {

        @TypeConverter
        @JvmStatic
        fun actorsToString(actors: MutableList<Actor>?): String? =
            actors?.let { Json.encodeToString(actors) }

        @TypeConverter
        @JvmStatic
        fun stringToActors(actors: String?): MutableList<Actor>? =
            actors?.let { Json.decodeFromString(it) }

        @TypeConverter
        @JvmStatic
        fun genresToString(genres: MutableList<Genre>?): String? =
            genres?.let { Json.encodeToString(genres) }


        @TypeConverter
        @JvmStatic
        fun stringToGenres(genres: String?): MutableList<Genre>? =
            genres?.let { Json.decodeFromString(it) }
    }
}