package com.englizya.model.model

import kotlinx.serialization.Serializable


@Serializable
class Bus {
    var BUSID: Int? = null
    var BUSNUMBER: Int? = null
    var BUSLETTER: String? = null
    var SERVICEDEGREEID: Int? = null
    var SEATNUMBER: Int? = null
    var AREAID: Int? = null
    var BUSSTATUS: String? = null
    var BRANDID: Int? = null
    var MODELID: Int? = null
    var MANUFACTYEAR: Int? = null
    var LICENSEAREAID: Int? = null
    var INSURANCECOMPANYID: Int? = null
    var INSURANCETYPEID: Int? = null
    var CHASSISNO: String? = null
    var MOTORNO: String? = null
    var BUSREMARK: String? = null
    var CREATEDBY: String? = null
    var CREATEDDATE: String? = null
    var COMPANYID: Int? = null
    var BRANCHID: Int? = null
    var LOCATIONID: Int? = null
    var seats : List<SeatDetails> = emptyList()
}