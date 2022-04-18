package com.englizya.complaint

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.complaint.util.ImageUtils.Companion.convertBitmapToFile
import com.englizya.datastore.UserDataStore
import com.englizya.repository.ComplaintRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ComplaintViewModel constructor(
    private val complaintRepository: ComplaintRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val intent = Channel<PostingIntent>()
    suspend fun sendIntent(intent: PostingIntent) {
        this.intent.send(intent)
    }

    init {
        triggerIntent()
    }

    private fun triggerIntent() {
        viewModelScope.launch(Dispatchers.IO) {
            intent.consumeAsFlow().collect {
                when (it) {
                    is PostingIntent.Post -> {
                        addPost(it.caption, it.image)
                    }
                }
            }
        }
    }

    private suspend fun addPost(caption: String, image: Bitmap) {
        complaintRepository.insertComplaint(
            token = userDataStore.getToken(),
            caption = caption,
            image = convertBitmapToFile(image),
        ).onSuccess {
//            state = PostingState.PostingCompleted(postId = postId.toInt())
        }.onFailure {
            handleException(it)
            state = PostingState.Error(it.message)
        }
    }

    private suspend fun createComplaintRequest() = kotlin.runCatching {
        ComplaintRe
    }
}
