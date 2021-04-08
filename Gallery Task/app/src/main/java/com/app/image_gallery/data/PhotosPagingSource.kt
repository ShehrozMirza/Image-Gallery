package com.app.image_gallery.data

import androidx.paging.PagingSource
import com.app.image_gallery.api.ApiService
import com.app.image_gallery.globals.APP_KEY
import com.app.image_gallery.globals.STARTING_PAGE_INDEX
import com.app.image_gallery.models.PhotosModel
import retrofit2.HttpException
import java.io.IOException

class PhotosPagingSource(private val apiService :ApiService, private val query: String) : PagingSource<Int, PhotosModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiService.searchPhotos(APP_KEY,query,position,params.loadSize)
            val photos = response.hits

            LoadResult.Page(
                    data = photos,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (photos.isEmpty()) null else position + 1
            )
        }catch (ioException: IOException){
            LoadResult.Error(ioException)
        } catch (httpException: HttpException){
            LoadResult.Error(httpException)
        }
    }
}