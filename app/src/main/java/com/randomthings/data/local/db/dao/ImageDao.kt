package com.randomthings.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.randomthings.data.local.db.entity.ImageEntity

@Dao
interface ImageDao {

    @Query("DELETE FROM images WHERE id = :id")
    suspend fun delete(id: String): Int
    //return int value, indicating the number of rows removed from the database.

    @Insert
    suspend fun insert(entity: ImageEntity): Long
    //return long, which is the new rowId for the inserted item

    @Query("SELECT * FROM images")
    fun getAll(): List<ImageEntity>

    @Query("SELECT * FROM images where id in(:ids)")
    fun getFromIds(ids: List<String>) : List<ImageEntity>
}
