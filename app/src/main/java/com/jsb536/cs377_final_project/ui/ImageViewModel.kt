package com.jsb536.cs377_final_project.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jsb536.cs377_final_project.ImageData
import com.jsb536.cs377_final_project.db.AppDatabase
import com.jsb536.cs377_final_project.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ImageRepository
    private val _searchResults = MutableLiveData<List<ImageData>>()
    val searchResults: LiveData<List<ImageData>> = _searchResults

    val savedResults: LiveData<List<ImageData>>

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        val imageDao = AppDatabase.getDatabase(application).imageDao()
        repository = ImageRepository(imageDao)
        savedResults = repository.getAllSavedImages()
    }

    fun searchImages(query: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val results = repository.searchImages(query, 1, 20)
                _searchResults.postValue(results)
            } catch (e: Exception) {
                _error.postValue("Failed to fetch images: ${e.message}")
                _searchResults.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun saveImageToDatabase(image: ImageData) {
        viewModelScope.launch {
            repository.saveImage(image)
        }
    }

    fun deleteImageFromDatabase(image: ImageData) {
        viewModelScope.launch {
            repository.deleteImage(image)
        }
    }

    fun checkIfImageIsSaved(image: ImageData){
        viewModelScope.launch(Dispatchers.IO){
            val isSaved = repository.isImageSaved(image.id)
            image.isSaved = isSaved
        }
    }
}