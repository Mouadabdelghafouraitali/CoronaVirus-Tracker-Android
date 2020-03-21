package maa.coronavirustracker_kotlin_vesion.UI.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maa.coronavirustracker_kotlin_vesion.Data.CoronaClient
import maa.coronavirustracker_kotlin_vesion.Models.Complete
import maa.coronavirustracker_kotlin_vesion.Models.Resume
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CoronaViewModel : ViewModel() {

    var mutableResumeLiveData: MutableLiveData<Resume> = MutableLiveData()
    var mutableCompleteLiveData: MutableLiveData<ArrayList<Complete>> = MutableLiveData()

    public fun getCoronaResumeInformation() {
        CoronaClient.coronaClient!!.coronaResumeInformation
            .enqueue(object : Callback<Resume> {
                override fun onFailure(call: Call<Resume>, t: Throwable) {
                    Log.d("Resume", t.message.toString())
                }

                override fun onResponse(call: Call<Resume>, response: Response<Resume>) {
                    if (response.body() != null) {
                        mutableResumeLiveData.setValue(
                            Resume(
                                response.body()!!.recovered,
                                response.body()!!.cases,
                                response.body()!!.deaths
                            )
                        )
                    }
                }
            })
    }

    fun getCoronaCompleteInformation() {
        val mData: ArrayList<Complete> = ArrayList()
        CoronaClient.coronaClient!!.coronaCompleteInformation
            .enqueue(object : Callback<List<Complete>> {
                override fun onResponse(
                    call: Call<List<Complete>>,
                    response: Response<List<Complete>>
                ) {
                    if (response.body() != null) {
                        for (i in response.body()!!.indices) {
                            mData.add(
                                Complete(
                                    response.body()!![i].country,
                                    response.body()!![i].recovered,
                                    response.body()!![i].cases,
                                    response.body()!![i].critical,
                                    response.body()!![i].deaths,
                                    response.body()!![i].todayCases,
                                    response.body()!![i].todayDeaths,
                                    response.body()!![i].active,
                                    response.body()!![i].casesPerOneMillion
                                )
                            )
                            mutableCompleteLiveData.setValue(mData)
                        }

                    }
                }

                override fun onFailure(
                    call: Call<List<Complete>?>,
                    t: Throwable
                ) {
                }
            })
    }
}