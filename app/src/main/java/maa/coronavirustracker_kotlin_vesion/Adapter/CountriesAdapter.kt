package maa.coronavirustracker_kotlin_vesion.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import maa.coronavirustracker_kotlin_vesion.Interfaces.RecyclerViewOnClickListener
import maa.coronavirustracker_kotlin_vesion.Model.Country
import maa.coronavirustracker_kotlin_vesion.R

class CountriesAdapter(
    var context: Context,
    var countriesList: ArrayList<Country>?,
    var onRecyclerViewOnClickListener: RecyclerViewOnClickListener
) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolderCountries>() {

    class ViewHolderCountries(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val countryFlag: ImageView = itemView.findViewById(R.id.countryFlag)
        val countryCases: TextView = itemView.findViewById(R.id.countryCases)
        lateinit var context: Context
        lateinit var onRecyclerViewOnClickListener: RecyclerViewOnClickListener
        var countriesList: ArrayList<Country>? = ArrayList()
        fun onBind(
            countries: Country,
            context: Context,
            onRecyclerViewOnClickListener: RecyclerViewOnClickListener,
            countriesList: ArrayList<Country>?
        ) {
            itemView.setOnClickListener(this)
            this.context = context
            this.onRecyclerViewOnClickListener = onRecyclerViewOnClickListener
            this.countriesList = countriesList
            countryName.setText(countries.country)
            countryCases.setText("%,d".format(countries.cases))
            Glide.with(context).load(countries.countryInfo.flag).into(countryFlag)
        }

        override fun onClick(v: View?) {
            onRecyclerViewOnClickListener.onRecyclerViewClickListener(
                adapterPosition,
                countriesList
            )
            // showBottomDialog(context)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCountries {
        val view: View = LayoutInflater.from(context).inflate(R.layout.itemcountry, parent, false)
        return ViewHolderCountries(view)
    }

    override fun getItemCount(): Int {
        return countriesList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolderCountries, position: Int) {
        holder.onBind(
            countriesList!!.get(position),
            context,
            onRecyclerViewOnClickListener,
            countriesList
        )
    }
}