package com.app.image_gallery.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.app.image_gallery.R
import com.app.image_gallery.models.PhotosModel
import com.app.image_gallery.databinding.FragmentPhotoViewBinding
import com.app.image_gallery.extra.ImageUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_photo_view.*

class PhotoViewFragment : Fragment(R.layout.fragment_photo_view) {

    private lateinit var bitmap: Bitmap
    private val args by navArgs<PhotoViewFragmentArgs>()

    private lateinit var binding: FragmentPhotoViewBinding
    private lateinit var  photo:PhotosModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        binding = FragmentPhotoViewBinding.bind(view)
        photo= args.photo
        bindViews()
        setListeners(photo)
    }

    private fun bindViews() {

        with(binding)
        {
            apply {

                Glide.with(this@PhotoViewFragment)
                        .load(photo.largeImageURL)
                        .error(R.drawable.ic_error)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                progressBar.isVisible = false
                                binding.layoutControls.isVisible = false
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                progressBar.isVisible = false
                                binding.layoutControls.isVisible = true
                                bitmap = (resource as BitmapDrawable).bitmap
                                return false
                            }
                        })
                        .into(binding.imageView)
            }


            photo.favorites?.let {
                clapFav.setClapCount(it)
                txtFav.text = it.toString()
            }

            photo.likes?.let {
                clapLike.setClapCount(it)
                txtLikes.text = it.toString()
            }

            photo.comments?.let {
                clapComments.setClapCount(it)
                txtComments.text = it.toString()
            }

            photo.downloads?.let { clapDownload.setClapCount(it)
                txtDownload.text = it.toString()
            }

            photo.views?.let {
                clapView.setClapCount(it)
                txtViews.text = it.toString()
            }

        }
    }

    private fun setListeners(photo: PhotosModel) {

        binding.clapShare.setOnClickListener {
            ImageUtils.shareImage(binding, requireContext())
        }

        binding.clapDownload.setOnClickListener {
           // ImageUtils.downloadImage(binding, photo, requireContext())
        }
    }
}