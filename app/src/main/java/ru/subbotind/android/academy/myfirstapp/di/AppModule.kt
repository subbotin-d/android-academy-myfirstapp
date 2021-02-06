package ru.subbotind.android.academy.myfirstapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.subbotind.android.academy.myfirstapp.data.database.MovieDataBase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "MOVIES_STORAGE",
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideMovieDataBase(@ApplicationContext context: Context): MovieDataBase =
        Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "movie_database"
        ).fallbackToDestructiveMigration()
            .build()
}