package com.interview.mywallst_assignment.data_payload

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MeteorsApiResponseItem (

    @SerializedName(":@computed_region_cbhk_fwbd")
    val computedRegionCbhkFwbd: String,
    @SerializedName(":@computed_region_nnqa_25f4")
    val computedRegionNnqa25f4: String,
    val fall: String,
   // val geolocation: Geolocation,
    val id: String,
    var mass: String,
    val name: String,
    val nametype: String,
    val recclass: String,
    val reclat: String,
    val reclong: String,
    val year: String
) : Parcelable