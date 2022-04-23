package com.englizya.api.impl

import com.englizya.api.SupportService
import com.englizya.api.utils.RequestParam.DESC
import com.englizya.api.utils.RequestParam.DRIVER_CODE
import com.englizya.api.utils.RequestParam.IMAGE
import com.englizya.api.utils.RequestParam.REVIEW
import com.englizya.api.utils.RequestParam.REVIEW_MESSAGE
import com.englizya.api.utils.RequestParam.TITLE
import com.englizya.api.utils.Routing
import com.englizya.model.request.ComplaintRequest
import com.englizya.model.request.DriverReviewRequest
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*

class SupportServiceImpl(
    private val client: HttpClient,
) : SupportService {

    @OptIn(InternalAPI::class)
    override suspend fun insertComplaint(
        complaintRequest: ComplaintRequest,
        token: String
    ): Int {
        return client.submitFormWithBinaryData(
            url = Routing.POST_COMPLAINT,
            formData = formData {
                append(TITLE, complaintRequest.complaintTitle)
                append(DESC, complaintRequest.complaintDesc)

                complaintRequest.complaintImage?.readBytes()?.let {
                    append(IMAGE, it)
                }
            }
        ) {
            headers.append(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun insertDriverReview(
        driverReviewRequest: DriverReviewRequest,
        token: String
    ): Int {
        return client.submitFormWithBinaryData(
            url = Routing.POST_DRIVER_REVIEW,
            formData = formData {
                append(REVIEW, driverReviewRequest.review)
                append(DRIVER_CODE, driverReviewRequest.driverCode)

                driverReviewRequest.reviewMessage?.let { append(REVIEW_MESSAGE, it) }

                driverReviewRequest.complaintImage?.readBytes()?.let {
                    append(IMAGE, it)
                }
            },
        ) {
            headers.append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}