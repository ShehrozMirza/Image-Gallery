package com.app.image_gallery.extra

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.app.image_gallery.databinding.FragmentPhotoViewBinding
import com.app.image_gallery.models.PhotosModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageUtils {

    companion object {
        fun shareImage(binding: FragmentPhotoViewBinding, context: Context) {
            binding.imageView.buildDrawingCache()
            val icon: Bitmap = binding.imageView.drawingCache
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/*"
            val bytes = ByteArrayOutputStream()
            icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val f = File(Environment.getDataDirectory(), "tempfile.jpg")
            try {
                f.createNewFile()
                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse(f.absolutePath))
            context.startActivity(Intent.createChooser(share, "Share Image"))
        }

        fun downloadImage(binding: FragmentPhotoViewBinding, photo: PhotosModel, context: Context) {
            if (TedPermission.isGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                binding.imageView.buildDrawingCache()
                val icon: Bitmap = binding.imageView.drawingCache
                val share = Intent(Intent.ACTION_SEND)
                share.type = "image/jpeg"
                val bytes = ByteArrayOutputStream()
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                val f = File(Environment.getRootDirectory(), "/justimages/" + photo.id)
                try {
                    f.createNewFile()
                    val fo = FileOutputStream(f)
                    fo.write(bytes.toByteArray())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {

                val permissionlistener: PermissionListener = object : PermissionListener {
                    override fun onPermissionGranted() {

                    }

                    override fun onPermissionDenied(deniedPermissions: List<String?>) {
                        Toast.makeText(context, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
                    }
                }
                TedPermission.with(context)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check()

            }
        }
    }
}