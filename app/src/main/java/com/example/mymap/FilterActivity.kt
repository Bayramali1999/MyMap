package com.example.mymap

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mymap.splash.FilterFragment
import com.example.mymap.splash.SplashFragment

@Suppress("DEPRECATION")
class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        supportFragmentManager.beginTransaction().replace(R.id.container, SplashFragment()).commit()
    }

    override fun onResume() {
        super.onResume()

        (Handler()).postDelayed({
            supportFragmentManager.beginTransaction().replace(R.id.container, FilterFragment())
                .commit()
        }, 3000)
    }
}