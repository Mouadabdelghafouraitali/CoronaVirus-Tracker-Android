package maa.coronavirustracker_kotlin_vesion.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import maa.coronavirustracker_kotlin_vesion.Model.Country
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.UI.CoronaViewModel

/**
 * A simple [Fragment] subclass.
 */
class Map : Fragment(), OnMapReadyCallback {

    lateinit var root: View
    lateinit var coronaViewModel: CoronaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_map, container, false)
        coronaViewModel = ViewModelProvider.NewInstanceFactory().create(CoronaViewModel::class.java)
        coronaViewModel.getCountriesInformation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)
        return root
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        coronaViewModel.getCountriesInformation()
        coronaViewModel.mutableCountriesLiveData.observe(this.viewLifecycleOwner,
            object : Observer<ArrayList<Country>> {
                override fun onChanged(listCountries: ArrayList<Country>?) {
                    for (index in listCountries!!.indices) {
                        googleMap!!.addMarker(
                            MarkerOptions().position(
                                    LatLng(
                                        listCountries[index].countryInfo.lat,
                                        listCountries[index].countryInfo.long
                                    )
                                )
                                .title(listCountries[index].country)
                        )
                    }
                }
            })

    }

}
