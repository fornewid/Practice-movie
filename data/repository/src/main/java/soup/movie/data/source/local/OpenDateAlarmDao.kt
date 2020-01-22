package soup.movie.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import soup.movie.data.model.entity.OpenDateAlarm

@Dao
interface OpenDateAlarmDao {

    @Query("SELECT * FROM open_date_alarms")
    fun getOpenDateAlarmList(): Flow<List<OpenDateAlarm>>

    @Query("SELECT COUNT(id) FROM open_date_alarms WHERE id = :movieId")
    suspend fun getOpenDateAlarmCount(movieId: String): Int

    @Insert(onConflict = REPLACE)
    suspend fun insertOpenDateAlarm(alarm: OpenDateAlarm)

    @Update
    suspend fun updateOpenDateAlarms(alarms: List<OpenDateAlarm>)

    @Query("DELETE FROM open_date_alarms WHERE id = :movieId")
    suspend fun deleteOpenDateAlarm(movieId: String)
}