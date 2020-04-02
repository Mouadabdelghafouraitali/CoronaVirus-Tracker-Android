package maa.coronavirustracker_kotlin_vesion.Data

import maa.coronavirustracker_kotlin_vesion.Constants.BASEURL
import maa.coronavirustracker_kotlin_vesion.Models.Complete
import maa.coronavirustracker_kotlin_vesion.Models.Resume
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CoronaClient {
    private val mCoronaInterface: CoronaInterface

    val coronaCompleteInformation: Call<List<Complete>> get() = mCoronaInterface.getCoronaVirusCompleteInformation()
    val coronaResumeInformation: Call<Resume> get() = mCoronaInterface.getCoronaVirusResumeInformation()

    companion object {
        private var mCoronaClient: CoronaClient? = null
        val coronaClient: CoronaClient? get() =
            if (mCoronaClient != null)
                mCoronaClient
            else {
                mCoronaClient = CoronaClient()
                mCoronaClient
            }
    }


    init {
        val mRetrofit: Retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        mCoronaInterface = mRetrofit.create(CoronaInterface::class.java)
    }
}
