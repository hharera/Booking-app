package com.englizya.model.model

import kotlinx.serialization.Serializable

@Serializable
class LinePath  {
    var lineId: Int? = null
    var lineNo: Int? = null
    var linePath: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var pathType: Int? = null
    var lineIcon: String? = null
}