package maa.coronavirustracker_kotlin_vesion.UI.Main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.databinding.ActivityDetailsBinding


class Details : AppCompatActivity(), OnChartValueSelectedListener {


    private lateinit var activityDetailsBinding: ActivityDetailsBinding
    private lateinit var chart: BarChart
    private lateinit var Country: String
    private lateinit var Cases: String
    private lateinit var TodayCases: String
    private lateinit var Deaths: String
    private lateinit var TodayDeaths: String
    private lateinit var Recovered: String
    private lateinit var Critical: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        chart = activityDetailsBinding.chart1
        getAllData()
        setupChart()
        setAllData()
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }


    @SuppressLint("DefaultLocale")
    private fun setAllData() {
        changeColorOfString(
            activityDetailsBinding.Country,
            "• Country : ",
            Country,
            getResources().getColor(R.color.TodayCasesColor)
        );
        changeColorOfString(
            activityDetailsBinding.Cases,
            "• Cases : ",
            String.format("%,d", (Integer.parseInt(Cases))),
            getResources().getColor(R.color.CasesColor)
        );
        changeColorOfString(
            activityDetailsBinding.TodayCases,
            "• Today cases : ",
            String.format("%,d", (Integer.parseInt(TodayCases))),
            getResources().getColor(R.color.TodayCasesColor)
        );
        changeColorOfString(
            activityDetailsBinding.Deaths,
            "• Deaths : ",
            String.format("%,d", (Integer.parseInt(Deaths))),
            getResources().getColor(R.color.DeathsColor)
        );
        changeColorOfString(
            activityDetailsBinding.TodayDeaths,
            "• Today deaths : ",
            String.format("%,d", (Integer.parseInt(TodayCases))),
            getResources().getColor(R.color.TodayDeathsColor)
        );
        changeColorOfString(
            activityDetailsBinding.recovered,
            "• Recovered : ",
            String.format("%,d", (Integer.parseInt(Recovered))),
            getResources().getColor(R.color.RecoveredColor)
        );
        changeColorOfString(
            activityDetailsBinding.Critical,
            "• Critical : ",
            String.format("%,d", (Integer.parseInt(Critical))),
            getResources().getColor(R.color.CriticalColor)
        );

    }

    private fun getAllData() {
        if (intent != null) {
            Country = intent.getStringExtra("Country");
            Cases = intent.getStringExtra("Cases");
            TodayCases = intent.getStringExtra("TodayCases");
            Deaths = intent.getStringExtra("Deaths");
            TodayDeaths = intent.getStringExtra("TodayDeaths");
            Recovered = intent.getStringExtra("Recovered");
            Critical = intent.getStringExtra("Critical");
            setData(
                Integer.parseInt(Cases),
                Integer.parseInt(TodayCases),
                Integer.parseInt(Deaths),
                Integer.parseInt(TodayDeaths),
                Integer.parseInt(Recovered),
                Integer.parseInt(Critical)
            );
        }


    }

    public fun setData(
        Cases: Int,
        TodayCases: Int,
        Deaths: Int,
        TodayDeaths: Int,
        Recovered: Int,
        Critical: Int
    ) {

        val values = ArrayList<BarEntry>()
        values.add(BarEntry(0f, Cases.toFloat()))
        values.add(BarEntry(1f, TodayCases.toFloat()))
        values.add(BarEntry(2f, Deaths.toFloat()))
        values.add(BarEntry(3f, TodayDeaths.toFloat()))
        values.add(BarEntry(4f, Recovered.toFloat()))
        values.add(BarEntry(5f, Critical.toFloat()))

        var set1: BarDataSet
        if (chart.getData() != null &&
            chart.getData().getDataSetCount() > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = BarDataSet(values, "Horizontal Chart");
            set1.setDrawIcons(false);
            val colors: ArrayList<Int> = ArrayList()
            colors.add(getResources().getColor(R.color.CasesColor));
            colors.add(getResources().getColor(R.color.TodayCasesColor));
            colors.add(getResources().getColor(R.color.DeathsColor));
            colors.add(getResources().getColor(R.color.TodayDeathsColor));
            colors.add(getResources().getColor(R.color.RecoveredColor));
            colors.add(getResources().getColor(R.color.CriticalColor));
            set1.setColors(colors);
            val dataSets: ArrayList<IBarDataSet> = ArrayList<IBarDataSet>();
            dataSets.add(set1);
            val data: BarData = BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chart.setData(data);
            chart.setNoDataTextColor(Color.BLACK);
            data.setValueTextColor(Color.BLACK);
            chart.getLegend().setTextColor(Color.BLACK);
        }

    }

    private fun setupChart() {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        val xl = chart.xAxis
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);
        val yl: YAxis = chart.axisLeft
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);
        val yr: YAxis = chart.axisRight
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);
        chart.setFitBars(true);
        chart.animateY(2500);
    }


    public fun changeColorOfString(
        textView: TextView,
        label: String,
        value: String,
        ColorHex: Int
    ) {
        var LabelValue: String = label + value;
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
