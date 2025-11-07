package com.randomthings.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.randomthings.data.local.db.entity.FavouriteDataType
import com.randomthings.data.local.db.entity.FavouriteEntity

@Dao
interface FavouriteDao {

    @Delete
    suspend fun delete(entity: FavouriteEntity): Int
    //return int value, indicating the number of rows removed from the database.

    @Insert
    suspend fun insert(entity: FavouriteEntity): Long
    //return long, which is the new rowId for the inserted item

    @Query("SELECT * FROM favourites")
    fun getAll(): List<FavouriteEntity>

    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE type = :type AND dataId = :dataId)")
    fun isRowIsExist(type: FavouriteDataType, dataId : String) : Boolean

    @Query("DELETE FROM favourites WHERE type = :type AND dataId = :dataId")
    suspend fun deleteByTypeAndDataId(type: FavouriteDataType, dataId: String): Int
}
