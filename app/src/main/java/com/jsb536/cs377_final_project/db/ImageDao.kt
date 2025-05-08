package com.jsb536.cs377_final_project.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jsb536.cs377_final_project.ImageData

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageData)

    @Query("SELECT * FROM saved_images")
    fun getAllSavedImages(): LiveData<List<ImageData>>

    @Delete
    suspend fun deleteImage(image: ImageData)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_images WHERE id = :imageId LIMIT 1)")
    suspend fun isImageSaved(imageId: String): Boolean
}