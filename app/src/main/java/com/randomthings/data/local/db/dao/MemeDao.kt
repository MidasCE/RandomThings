package com.randomthings.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.randomthings.data.local.db.entity.MemeEntity

@Dao
interface MemeDao {

    @Query("DELETE FROM memes WHERE postLink = :postLink")
    suspend fun delete(postLink: String): Int
    //return int value, indicating the number of rows removed from the database.

    @Insert
    suspend fun insert(entity: MemeEntity): Long
    //return long, which is the new rowId for the inserted item

    @Query("SELECT * FROM memes")
    fun getAll(): List<MemeEntity>

    @Query("SELECT * FROM memes where postLink in(:postLinks)")
    fun getFromPostlinks(postLinks: List<String>) : List<MemeEntity>
}
