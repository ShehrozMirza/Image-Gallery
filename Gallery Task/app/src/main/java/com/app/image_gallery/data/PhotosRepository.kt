package com.app.image_gallery.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.app.image_gallery.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(private val apiService: ApiService) {

    fun getSearchResults(query: String) =
            Pager(
                    config = PagingConfig(20,
                            maxSize = 100,
                            enablePlaceholders = false
                    )
                    , pagingSourceFactory = { PhotosPagingSource(apiService, query) }
            )
            .liveData
}