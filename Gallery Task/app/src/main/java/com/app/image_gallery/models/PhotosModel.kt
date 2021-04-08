package com.app.image_gallery.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosModel(val id: String?, val previewURL: String?, val largeImageURL: String?,
                       val fullHDURL: String?, val imageURL: String?
                       ,val views: Int?,val downloads: Int?,val favorites: Int?,val likes: Int?
                       ,val comments: Int?,val user_id: Int?,val user: String?,val tags :String?) : Parcelable
