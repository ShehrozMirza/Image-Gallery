package com.app.image_gallery.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.image_gallery.R
import com.app.image_gallery.models.PhotosModel
import com.app.image_gallery.databinding.ItemPhotoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PhotosAdapter(private val onItemClickListener: OnItemClickListener) : PagingDataAdapter<PhotosModel, PhotosAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null)
                        onItemClickListener.onItemClick(item)
                }
            }
        }

        fun bind(photo: PhotosModel) {

            with(binding)
            {
                apply {
                    Glide.with(binding.imgView)
                            .load(photo.previewURL)
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_error)
                            .into(binding.imgView)
                }
                binding.txtTags.text = photo.tags
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: PhotosModel)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PhotosModel>() {
            override fun areItemsTheSame(oldItem: PhotosModel, newItem: PhotosModel): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: PhotosModel, newItem: PhotosModel) = oldItem == newItem
        }
    }
}