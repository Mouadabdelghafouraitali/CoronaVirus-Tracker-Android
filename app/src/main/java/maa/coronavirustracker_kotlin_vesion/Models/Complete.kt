package maa.coronavirustracker_kotlin_vesion.Models

import com.google.gson.annotations.SerializedName


data class Complete(
    @SerializedName("country") val country: String,
    @SerializedName("recovered") val recovered: Int,
    @SerializedName("cases") val cases: Int,
    @SerializedName("critical") val critical: Int,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("todayCases") val todayCases: Int,
    @SerializedName("todayDeaths") val todayDeaths: Int,
    @SerializedName("active") val active: Int,
    @SerializedName("casesPerOneMillion") val casesPerOneMillion: Int
)