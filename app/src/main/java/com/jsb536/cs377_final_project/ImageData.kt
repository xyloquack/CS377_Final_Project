package com.jsb536.cs377_final_project

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_images")
data class ImageData(
    @PrimaryKey val id: String,
    val description: String?,
    val imageUrlSmall: String,
    val imageUrlRegular: String,
    val photographerName: String?,
    var isSaved: Boolean = false
) : Parcelable

data class UnsplashSearchResponse(
    val results: List<UnsplashPhoto>
)

data class UnsplashPhoto(
    val id: String,
    val description: String?,
    val altDescription: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
)

data class UnsplashPhotoUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class UnsplashUser(
    val name: String,
    val username: String
)

fun UnsplashPhoto.toImageData(): ImageData {
    return ImageData(
        id = this.id,
        description = this.description ?: this.altDescription,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        photographerName = this.user.name
    )
}