package maa.coronavirustracker_kotlin_vesion.Fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog.*
import kotlinx.android.synthetic.main.itemcountry.countryFlag
import kotlinx.android.synthetic.main.itemcountry.countryName
import maa.coronavirustracker_kotlin_vesion.Adapter.CountriesAdapter
import maa.coronavirustracker_kotlin_vesion.Interfaces.RecyclerViewOnClickListener
import maa.coronavirustracker_kotlin_vesion.Model.Country
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.UI.CoronaViewModel

/**
 * A simple [Fragment] subclass.
 */
class Countries : Fragment() {
    lateinit var root: View
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var countriesAdapter: CountriesAdapter
    lateinit var coronaViewModel: CoronaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_countries, container, false)
        initViews(root)
        coronaViewModel = ViewModelProvider.NewInstanceFactory().create(CoronaViewModel::class.java)
        coronaViewModel.getCountriesInformation()
        coronaViewModel.mutableCountriesLiveData.observe(this.viewLifecycleOwner,
            object : Observer<ArrayList<Country>> {
                override fun onChanged(listCountries: ArrayList<Country>?) {

                    Toast.makeText(
                        context,
                        "SLAM : " + listCountries!!.trimToSize(),
                        Toast.LENGTH_SHORT
                    ).show()

                    progressBar.visibility = View.GONE
                    countriesAdapter = CountriesAdapter(
                        activity!!.applicationContext,
                        listCountries,
                        object : RecyclerViewOnClickListener {
                            override fun onRecyclerViewClickListener(
                                position: Int,
                                country: ArrayList<Country>?
                            ) {
                                val mBottomSheetDialog = RoundedBottomSheetDialog(root.context)
                                val sheetView = LayoutInflater.from(context)
                                    .inflate(R.layout.bottom_dialog, null)
                                mBottomSheetDialog.setContentView(sheetView)
                                Glide.with(activity!!.applicationContext)
                                    .load(country!![position].countryInfo.flag)
                                    .into(mBottomSheetDialog.countryFlag)
                                mBottomSheetDialog.countryName.setText(country[position].country)

                                setCustomColor(
                                    mBottomSheetDialog.Cases,
                                    "• Cases : ",
                                    String.format("%,d", (country[position].cases)),
                                    getResources().getColor(R.color.CasesColor)
                                )
                                setCustomColor(
                                    mBottomSheetDialog.TodayCases,
                                    "• Today cases : ",
                                    String.format("%,d", (country[position].todayCases)),
                                    getResources().getColor(R.color.TodayCasesColor)
                                )
                                setCustomColor(
                                    mBottomSheetDialog.Deaths,
                                    "• Deaths : ",
                                    String.format("%,d", (country[position].deaths)),
                                    getResources().getColor(R.color.DeathsColor)
                                )
                                setCustomColor(
                                    mBottomSheetDialog.TodayDeaths,
                                    "• Today deaths : ",
                                    String.format("%,d", (country[position].todayDeaths)),
                                    getResources().getColor(R.color.TodayDeathsColor)
                                )
                                setCustomColor(
                                    mBottomSheetDialog.recovered,
                                    "• Recovered : ",
                                    String.format("%,d", (country[position].recovered)),
                                    getResources().getColor(R.color.RecoveredColor)
                                )
                                setCustomColor(
                                    mBottomSheetDialog.Critical,
                                    "• Critical : ",
                                    String.format("%,d", (country[position].critical)),
                                    getResources().getColor(R.color.CriticalColor)
                                )
                                setCustomColor(
                                    mBottomSheetDialog.active,
                                    "• Active : ",
                                    String.format("%,d", (country[position].active)),
                                    Color.BLACK
                                )
                                setCustomColor(
                                    mBottomSheetDialog.casesPerOneMillion,
                                    "• Cases/1M : ",
                                    String.format(
                                        "%,d",
                                        (country[position].casesPerOneMillion!!.toInt())
                                    ), Color.BLUE
                                )
                                mBottomSheetDialog.show()

                            }
                        })
                    recyclerView.adapter = countriesAdapter
                }
            })
        return root
    }

    private fun initViews(root: View) {
        progressBar = root.findViewById(R.id.loading)
        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(root.context, 1)
        recyclerView.hasFixedSize()
    }

    public fun setCustomColor(
        textView: TextView,
        label: String,
        value: String,
        ColorHex: Int
    ) {
        val LabelValue: String = label + value;
        val spannable: Spannable = SpannableString(LabelValue);
        spannable.setSpan(
            ForegroundColorSpan(ColorHex),
            label.length,
            (label + value).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
            android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            label.length,
            (label + value).length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

}
