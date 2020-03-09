package maa.coronavirustracker_kotlin_vesion.Data

import maa.coronavirustracker_kotlin_vesion.Models.Complete
import maa.coronavirustracker_kotlin_vesion.Models.Resume
import retrofit2.Call
import retrofit2.http.GET


interface CoronaInterface {
    @GET("all")
    fun getCoronaVirusResumeInformation(): Call<Resume>

    @GET("countries")
    fun getCoronaVirusCompleteInformation(): Call<List<Complete>>
}