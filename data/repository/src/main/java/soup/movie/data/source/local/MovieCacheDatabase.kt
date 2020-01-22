package soup.movie.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import soup.movie.data.model.converter.MovieListTypeConverter
import soup.movie.data.model.entity.CachedMovieList

@Database(entities = [CachedMovieList::class], version = 3, exportSchema = false)
@TypeConverters(MovieListTypeConverter::class)
abstract class MovieCacheDatabase : RoomDatabase() {

    abstract fun movieCacheDao(): MovieCacheDao
}