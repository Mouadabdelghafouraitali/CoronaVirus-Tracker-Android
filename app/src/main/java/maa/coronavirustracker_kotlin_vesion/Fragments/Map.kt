package maa.coronavirustracker_kotlin_vesion.Fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.bottom_dialog.*
import kotlinx.android.synthetic.main.itemcountry.countryFlag
import kotlinx.android.synthetic.main.itemcountry.countryName
import maa.coronavirustracker_kotlin_vesion.Helper
import maa.coronavirustracker_kotlin_vesion.Model.Country
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.UI.CoronaViewModel

/**
 * A simple [Fragment] subclass.
 */
class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    lateinit var root: View
    lateinit var coronaViewModel: CoronaViewModel
    lateinit var helper: Helper
    private lateinit var mMap: GoogleMap

    var countries: ArrayList<Country> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_map, container, false)
        coronaViewModel = ViewModelProvider.NewInstanceFactory().create(CoronaViewModel::class.java)
        coronaViewModel.getCountriesInformation()
        helper = Helper()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)
        return root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
            mMap.setOnMarkerClickListener(this)
            coronaViewModel.getCountriesInformation()
            coronaViewModel.mutableCountriesLiveData.observe(this.viewLifecycleOwner,
                object : Observer<ArrayList<Country>> {
                    override fun onChanged(listCountries: ArrayList<Country>?) {
                        if (listCountries != null) {
                            countries = listCountries
                            for (index in listCountries.indices) {
                                googleMap.addMarker(
                                    MarkerOptions()
                                        .position(
                                            LatLng(
                                                listCountries[index].countryInfo.lat,
                                                listCountries[index].countryInfo.long
                                            )
                                        )
                                        .title(listCountries[index].country)
                                        .snippet(index.toString())
                                )

                            }
                        }
                    }
                })
        }
    }


    fun showBottomDialog(country: ArrayList<Country>?, position: Int) {
        val mBottomSheetDialog = RoundedBottomSheetDialog(root.context)
        val sheetView = LayoutInflater.from(context)
            .inflate(R.layout.bottom_dialog, null)
        mBottomSheetDialog.setContentView(sheetView)
        Glide.with(activity!!.applicationContext)
            .load(country!![position].countryInfo.flag)
            .into(mBottomSheetDialog.countryFlag)
        mBottomSheetDialog.countryName.setText(country[position].country)
        helper.setCustomColor(
            mBottomSheetDialog.Cases,
            "• Cases : ",
            String.format("%,d", (country[position].cases)),
            getResources().getColor(R.color.CasesColor)
        )
        helper.setCustomColor(
            mBottomSheetDialog.TodayCases,
            "• Today cases : ",
            String.format("%,d", (country[position].todayCases)),
            getResources().getColor(R.color.TodayCasesColor)
        )
        helper.setCustomColor(
            mBottomSheetDialog.Deaths,
            "• Deaths : ",
            String.format("%,d", (country[position].deaths)),
            getResources().getColor(R.color.DeathsColor)
        )
        helper.setCustomColor(
            mBottomSheetDialog.TodayDeaths,
            "• Today deaths : ",
            String.format("%,d", (country[position].todayDeaths)),
            getResources().getColor(R.color.TodayDeathsColor)
        )
        helper.setCustomColor(
            mBottomSheetDialog.recovered,
            "• Recovered : ",
            String.format("%,d", (country[position].recovered)),
            getResources().getColor(R.color.RecoveredColor)
        )
        helper.setCustomColor(
            mBottomSheetDialog.Critical,
            "• Critical : ",
            String.format("%,d", (country[position].critical)),
            getResources().getColor(R.color.CriticalColor)
        )
        helper.setCustomColor(
            mBottomSheetDialog.active,
            "• Active : ",
            String.format("%,d", (country[position].active)),
            Color.BLACK
        )
        helper.setCustomColor(
            mBottomSheetDialog.casesPerOneMillion,
            "• Cases/1M : ",
            String.format(
                "%,d",
                (country[position].casesPerOneMillion!!.toInt())
            ), Color.BLUE
        )
        mBottomSheetDialog.show()
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Log.d("onMarkerClick:", "Hi")
        showBottomDialog(countries, Integer.parseInt(marker!!.snippet))
        return true
    }


}
