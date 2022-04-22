package com.englizya.common.utils

object ImageUtils {

    fun convertBitmapToFile(bitmap: android.graphics.Bitmap): java.io.File {
        val file = java.io.File.createTempFile("image", ".jpg")
        val outputStream: java.io.OutputStream =
            java.io.BufferedOutputStream(java.io.FileOutputStream(file))
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 30, outputStream)
        outputStream.close()
        return file
    }

    fun convertImagePathToBitmap(data: android.content.Intent): android.graphics.Bitmap? {
        val imagesList =
            data.extras?.getStringArray(com.opensooq.supernova.gligar.GligarPicker.Companion.IMAGES_RESULT)
        if (!imagesList.isNullOrEmpty()) {
            android.graphics.BitmapFactory.decodeFile(imagesList[0])?.let {
                return it
            }
        }
        return null
    }
}