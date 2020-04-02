package maa.coronavirustracker_kotlin_vesion.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import maa.coronavirustracker_kotlin_vesion.Model.All
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.UI.CoronaViewModel

class Overview : Fragment() {
    lateinit var root: View
    lateinit var progressBar: ProgressBar
    lateinit var totalCases: TickerView
    lateinit var totalDeaths: TickerView
    lateinit var totalRecovered: TickerView
    lateinit var active: TickerView
    lateinit var coronaViewModel: CoronaViewModel
    lateinit var overview: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_overview, container, false)
        initViews(root)
        coronaViewModel = ViewModelProvider.NewInstanceFactory().create(CoronaViewModel::class.java)
        coronaViewModel.getAllInformation()
        coronaViewModel.mutableAllLiveData.observe(this.viewLifecycleOwner, object : Observer<All> {
            override fun onChanged(values: All?) {
                progressBar.visibility = View.GONE
                overview.visibility = View.VISIBLE
                totalCases.setText("%,d".format(values!!.cases))
                totalDeaths.setText("%,d".format(values.deaths))
                totalRecovered.setText("%,d".format(values.recovered))
                active.setText("%,d".format(values.active))

            }
        })
        return root
    }


    fun initViews(root: View) {
        overview = root.findViewById(R.id.overviewLayout)
        progressBar = root.findViewById(R.id.loading)
        totalCases = root.findViewById(R.id.totalcases)
        totalDeaths = root.findViewById(R.id.totalDeaths)
        totalRecovered = root.findViewById(R.id.totalRecovered)
        active = root.findViewById(R.id.active)
        initTickerViews()

    }

    fun initTickerViews() {
        totalCases.setCharacterLists(TickerUtils.provideNumberList());
        totalDeaths.setCharacterLists(TickerUtils.provideNumberList());
        totalRecovered.setCharacterLists(TickerUtils.provideNumberList());
        active.setCharacterLists(TickerUtils.provideNumberList());
        totalCases.animationDuration = 1000
        totalDeaths.animationDuration = 1000
        totalRecovered.animationDuration = 1000
        active.animationDuration = 1000
    }
}
