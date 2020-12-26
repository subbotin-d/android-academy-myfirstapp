package ru.subbotind.android.academy.myfirstapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.subbotind.android.academy.myfirstapp.domain.MovieInteractor
import ru.subbotind.android.academy.myfirstapp.domain.MovieInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface MoviesModule {

    @Singleton
    @Binds
    fun provideMovieInteractor(movieInteractor: MovieInteractorImpl): MovieInteractor
}