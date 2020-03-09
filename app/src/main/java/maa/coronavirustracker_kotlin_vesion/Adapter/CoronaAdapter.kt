package maa.coronavirustracker_kotlin_vesion.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import maa.coronavirustracker_kotlin_vesion.Interfaces.onRecyclerViewItemClick
import maa.coronavirustracker_kotlin_vesion.Models.Complete
import maa.coronavirustracker_kotlin_vesion.R
import maa.coronavirustracker_kotlin_vesion.UI.Main.Details


class CoronaAdapter(
    var mContext: Context,
    var data: ArrayList<Complete>,
    var mRecyclerViewItemClick: onRecyclerViewItemClick
) : RecyclerView.Adapter<CoronaAdapter.CoronaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoronaViewHolder {
        val mView: View = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false)
        return CoronaViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CoronaViewHolder, position: Int) {
        holder.onBind(position, data)
    }

    class CoronaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var data: ArrayList<Complete> = ArrayList()
        val mCountry: TextView = itemView.findViewById(R.id.Country)
        val mNum: TextView = itemView.findViewById(R.id.Num)
        fun onBind(
            position: Int,
            data: ArrayList<Complete>
        ) {
            this.data = data
            mNum.setText(String.format("%,d", Integer.parseInt(data.get(position).cases)));
            mCountry.setText(data.get(position).country);
        }
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val Go: Intent = Intent(v!!.context, Details::class.java)
            Go.putExtra("Country", data.get(adapterPosition).country)
            Go.putExtra("Cases", data.get(adapterPosition).cases)
            Go.putExtra("TodayCases", data.get(adapterPosition).todayCases)
            Go.putExtra("Deaths", data.get(adapterPosition).deaths)
            Go.putExtra("TodayDeaths", data.get(adapterPosition).todayDeaths)
            Go.putExtra("Recovered", data.get(adapterPosition).recovered)
            Go.putExtra("Critical", data.get(adapterPosition).critical)
            v.context.startActivity(Go)
        }
    }
}