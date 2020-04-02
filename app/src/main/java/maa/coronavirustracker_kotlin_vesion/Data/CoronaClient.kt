package maa.coronavirustracker_kotlin_vesion.Data

import maa.coronavirustracker_kotlin_vesion.Model.All
import maa.coronavirustracker_kotlin_vesion.Model.Country
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CoronaClient {

    private var mCoronaInterface: CoronaInterface? = null


    init {
        val mRetrofit: Retrofit = Retrofit.Builder().baseUrl("https://corona.lmao.ninja/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        mCoronaInterface = mRetrofit.create(CoronaInterface::class.java)
    }

    companion object {
        private var mCoronaClient: CoronaClient? = null
        fun getCoronaClient(): CoronaClient? {
            return if (mCoronaClient != null) mCoronaClient else {
                mCoronaClient = CoronaClient()
                mCoronaClient
            }
        }
    }


    fun getCoronaAllInformation(): Call<All> {
        return mCoronaInterface!!.getCoronaVirusAllInformation()
    }

    fun getCoronaVirusByCountriesInformation(): Call<ArrayList<Country>> {
        return mCoronaInterface!!.getCoronaVirusByCountriesInformation()
    }

}