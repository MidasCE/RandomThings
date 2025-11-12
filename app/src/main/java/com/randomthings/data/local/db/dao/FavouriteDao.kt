package com.randomthings.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.randomthings.data.local.db.entity.FavouriteDataType
import com.randomthings.data.local.db.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert
    suspend fun insert(entity: FavouriteEntity): Long
    //return long, which is the new rowId for the inserted item

    @Query("SELECT * FROM favourites")
    fun getAll(): Flow<List<FavouriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE type = :type AND dataId = :dataId)")
    fun isRowIsExist(type: FavouriteDataType, dataId : String) : Flow<Boolean>

    @Query("DELETE FROM favourites WHERE type = :type AND dataId = :dataId")
    suspend fun deleteByTypeAndDataId(type: FavouriteDataType, dataId: String): Int

    @Query("SELECT dataId FROM favourites WHERE type = :type")
    fun getFavouriteIdsByType(type: FavouriteDataType): Flow<List<String>>
}
