package com.app.image_gallery.models

data class PixabayApiResponse(val total : Int, val totalHits : Int, val hits : List<PhotosModel>)