package com.jsb536.cs377_final_project.ui

import android.app.Application
import androidx.lifecycle.*
import com.jsb536.cs377_final_project.ImageData
import com.jsb536.cs377_final_project.db.AppDatabase
import com.jsb536.cs377_final_project.repository.ImageRepository
import kotlinx.coroutines.launch

class ImageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ImageRepository
    private val _searchResults = MutableLiveData<List<ImageData>>()
    val searchResults: LiveData<List<ImageData>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error


    init {
        val imageDao = AppDatabase.getDatabase(application).imageDao()
        repository = ImageRepository(imageDao)
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

    // --- Actions from Detail View ---
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
}