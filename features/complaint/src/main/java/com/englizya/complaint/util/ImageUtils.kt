package com.englizya.complaint.util

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.opensooq.supernova.gligar.GligarPicker
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


object ImageUtils {

    fun convertBitmapToFile(bitmap: Bitmap): File {
        val file = File.createTempFile("image", ".jpg")
        val outputStream: OutputStream = BufferedOutputStream(FileOutputStream(file))
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        outputStream.close()
        return file
    }

    fun convertImagePathToBitmap(data: Intent): Bitmap? {
        val imagesList = data.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
        if (!imagesList.isNullOrEmpty()) {
            BitmapFactory.decodeFile(imagesList[0])?.let {
                return it
            }
        }
        return null
    }
}