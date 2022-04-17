package com.englizya.complaint

import android.graphics.Bitmap

sealed class PostingIntent {
    data class Post(val caption: String, val image: Bitmap) : PostingIntent()
    object None : PostingIntent()
}
