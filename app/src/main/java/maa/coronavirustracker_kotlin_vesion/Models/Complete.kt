package maa.coronavirustracker_kotlin_vesion.Models

import com.google.gson.annotations.SerializedName


data class Complete(
    @SerializedName("country") val country: String,
    @SerializedName("recovered") val recovered: String,
    @SerializedName("cases") val cases: String,
    @SerializedName("critical") val critical: String,
    @SerializedName("deaths") val deaths: String,
    @SerializedName("todayCases") val todayCases: String,
    @SerializedName("todayDeaths") val todayDeaths: String
)