package ru.subbotind.android.academy.myfirstapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.subbotind.android.academy.myfirstapp.data.dao.MovieDao
import ru.subbotind.android.academy.myfirstapp.data.entity.Converters
import ru.subbotind.android.academy.myfirstapp.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}