package maa.coronavirustracker_kotlin_vesion.Models

import com.google.gson.annotations.SerializedName


data class Resume(
    @SerializedName("recovered") var recovered: String,
    @SerializedName("cases") var cases: String,
    @SerializedName("deaths") var deaths: String
)