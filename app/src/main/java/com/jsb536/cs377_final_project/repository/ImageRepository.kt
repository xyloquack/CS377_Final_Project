package com.jsb536.cs377_final_project.repository

import com.jsb536.cs377_final_project.api.RetrofitInstance
import com.jsb536.cs377_final_project.ImageData
import com.jsb536.cs377_final_project.db.ImageDao
import com.jsb536.cs377_final_project.toImageData

class ImageRepository(private val imageDao: ImageDao) {

    suspend fun searchImages(query: String, page: Int, perPage: Int): List<ImageData> {
        return try {
            val response = RetrofitInstance.api.searchPhotos(query, page, perPage)
            response.results.map { it.toImageData() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveImage(image: ImageData) {
        imageDao.insertImage(image.copy(isSaved = true))
    }

    suspend fun deleteImage(image: ImageData) {
        imageDao.deleteImage(image)
    }
}