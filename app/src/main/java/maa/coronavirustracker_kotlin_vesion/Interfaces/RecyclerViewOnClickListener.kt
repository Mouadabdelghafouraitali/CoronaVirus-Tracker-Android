package maa.coronavirustracker_kotlin_vesion.Interfaces

import maa.coronavirustracker_kotlin_vesion.Model.Country

interface RecyclerViewOnClickListener {

    fun onRecyclerViewClickListener(position: Int, country: ArrayList<Country>?)
}