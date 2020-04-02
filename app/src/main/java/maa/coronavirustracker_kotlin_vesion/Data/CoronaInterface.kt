package maa.coronavirustracker_kotlin_vesion.Data


import maa.coronavirustracker_kotlin_vesion.Model.All
import maa.coronavirustracker_kotlin_vesion.Model.Country
import retrofit2.Call
import retrofit2.http.GET

interface CoronaInterface {
    @GET("all")
    fun getCoronaVirusAllInformation(): Call<All>

    @GET("countries")
    fun getCoronaVirusByCountriesInformation(): Call<ArrayList<Country>>
}