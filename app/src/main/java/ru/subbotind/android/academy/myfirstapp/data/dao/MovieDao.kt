package ru.subbotind.android.academy.myfirstapp.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.academy.myfirstapp.data.entity.MovieEntity

@Dao
interface MovieDao {

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie_table ORDER BY last_update DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_table WHERE id=:id")
    fun getMovie(id: Int): Flow<MovieEntity>
}