package maa.coronavirustracker_kotlin_vesion.UI

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maa.coronavirustracker_kotlin_vesion.Data.CoronaClient
import maa.coronavirustracker_kotlin_vesion.Model.All
import maa.coronavirustracker_kotlin_vesion.Model.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoronaViewModel : ViewModel() {

    var mutableAllLiveData: MutableLiveData<All> = MutableLiveData()
    var mutableCountriesLiveData: MutableLiveData<ArrayList<Country>> = MutableLiveData()

    fun getAllInformation() {
        CoronaClient.getCoronaClient()!!.getCoronaAllInformation().enqueue(object : Callback<All> {
            override fun onFailure(call: Call<All>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

            override fun onResponse(call: Call<All>, response: Response<All>) {
                if (response.isSuccessful && response.body() != null && response.body().toString()
                        .isNotEmpty()
                ) {
                    mutableAllLiveData.postValue(
                        All(
                            response.body()!!.cases,
                            response.body()!!.deaths,
                            response.body()!!.recovered,
                            response.body()!!.updated,
                            response.body()!!.active,
                            response.body()!!.affectedCountries
                        )
                    )
                }

            }
        })
    }

    fun getCountriesInformation() {
        val listCountries: ArrayList<Country> = ArrayList()
        CoronaClient.getCoronaClient()!!.getCoronaVirusByCountriesInformation()
            .enqueue(object : Callback<ArrayList<Country>> {
                override fun onFailure(call: Call<ArrayList<Country>>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<Country>>,
                    response: Response<ArrayList<Country>>
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()
                            .toString().isNotEmpty()
                    ) {
                        for (index in response.body()!!.indices) {
                            listCountries.add(
                                Country(
                                    response.body()!![index].country,
                                    response.body()!![index].countryInfo,
                                    response.body()!![index].cases,
                                    response.body()!![index].todayCases,
                                    response.body()!![index].deaths,
                                    response.body()!![index].todayDeaths,
                                    response.body()!![index].recovered,
                                    response.body()!![index].active,
                                    response.body()!![index].critical,
                                    response.body()!![index].casesPerOneMillion,
                                    response.body()!![index].deathsPerOneMillion,
                                    response.body()!![index].updated
                                )
                            )
                        }
                        mutableCountriesLiveData.postValue(listCountries)
                    }
                }
            })
    }
}