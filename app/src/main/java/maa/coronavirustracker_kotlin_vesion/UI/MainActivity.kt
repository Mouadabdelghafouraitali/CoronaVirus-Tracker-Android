package maa.coronavirustracker_kotlin_vesion.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import maa.coronavirustracker_kotlin_vesion.Fragments.Countries
import maa.coronavirustracker_kotlin_vesion.Fragments.Map
import maa.coronavirustracker_kotlin_vesion.Fragments.Overview
import maa.coronavirustracker_kotlin_vesion.R
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {

    lateinit var bottomBar: SmoothBottomBar
    lateinit var title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar = findViewById(R.id.bottomBar)
        title = findViewById(R.id.title)
        goToFragment(Overview())
        bottomBar.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                navigate(pos)
            }
        })

    }

    @SuppressLint("SetTextI18n")
    fun navigate(position: Int) {
        when (position) {
            0 -> {
                title.setText("OVERVIEW");goToFragment(Overview())
            }
            1 -> {
                title.setText("COUNTRIES");goToFragment(Countries())
            }
            2 -> {
                title.setText("MAP");goToFragment(Map())
            }
        }
    }

    fun goToFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment, fragment.tag)
        fragmentTransaction.commit()
    }
}
