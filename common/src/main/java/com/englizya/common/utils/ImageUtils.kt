package com.englizya.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

object ImageUtils {

    fun convertBitmapToFile(bitmap: Bitmap?): java.io.File? {
        val file = java.io.File.createTempFile("image", ".jpg")
        val outputStream: java.io.OutputStream =
            java.io.BufferedOutputStream(java.io.FileOutputStream(file))
        bitmap?.compress(android.graphics.Bitmap.CompressFormat.JPEG, 30, outputStream)
        outputStream.close()
        return file
    }


    fun getImageFromUri(imageUri: Uri?, context: Context): Bitmap? {
        imageUri?.let {
            return if (Build.VERSION.SDK_INT < 28) {
                MediaStore
                    .Images
                    .Media
                    .getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return null
    }

    fun convertImagePathToBitmap(uri: Uri?): Bitmap? =
        BitmapFactory.decodeFile(uri?.path)
}