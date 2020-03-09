package maa.coronavirustracker_kotlin_vesion.UI.Main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import maa.coronavirustracker_kotlin_vesion.Adapter.CoronaAdapter
import maa.coronavirustracker_kotlin_vesion.Interfaces.onRecyclerViewItemClick
import maa.coronavirustracker_kotlin_vesion.Models.Complete
import maa.coronavirustracker_kotlin_vesion.Models.Resume
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    lateinit var mLayout: RecyclerView.LayoutManager
    lateinit var mCoronaAdapter: CoronaAdapter
    lateinit var mDataList: ArrayList<Complete>
    lateinit var mCoronaViewModel: CoronaViewModel
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCoronaViewModel =
            ViewModelProvider(this, NewInstanceFactory()).get(CoronaViewModel::class.java)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView(mainBinding.RecyclerViewData);
        mCoronaViewModel.getCoronaResumeInformation();
        mCoronaViewModel.mutableResumeLiveData.observe(this, object : Observer<Resume> {
            override fun onChanged(resume: Resume?) {
                displayViews(true);
                mainBinding.Confirmed.setText(
                    String.format(
                        "%,d",
                        (Integer.parseInt(resume!!.cases))
                    )
                );
                mainBinding.Recovered.setText(
                    String.format(
                        "%,d",
                        (Integer.parseInt(resume.recovered))
                    )
                );
                mainBinding.Deaths.setText(
                    String.format(
                        "%,d",
                        (Integer.parseInt(resume.deaths))
                    )
                );
            }
        })

        mCoronaViewModel.getCoronaCompleteInformation();
        mCoronaViewModel.mutableCompleteLiveData.observe(this, object : Observer<ArrayList<Complete>> {
            override fun onChanged(completes: ArrayList<Complete>) {
                mDataList = completes
                mCoronaAdapter =
                    CoronaAdapter(
                        applicationContext,
                        mDataList,
                        object : onRecyclerViewItemClick {
                            override fun onRecyclerViewItemClick(position: Int) {
                                //
                            }
                        })

                mainBinding.RecyclerViewData.adapter = mCoronaAdapter
            }
        })
    }


    fun displayViews(visibility: Boolean) {
        if (visibility) {
            mainBinding.Status.visibility = View.VISIBLE
            mainBinding.Loading.visibility = View.GONE
        } else {
            mainBinding.Status.visibility = View.GONE
            mainBinding.Loading.visibility = View.VISIBLE
        }
    }


    fun setupRecyclerView(mRecyclerView: RecyclerView) {
        mLayout = GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayout);
        mRecyclerView.setHasFixedSize(true);
    }
}
