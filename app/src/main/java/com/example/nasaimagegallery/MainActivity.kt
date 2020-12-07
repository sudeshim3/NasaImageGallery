package com.example.nasaimagegallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaimagegallery.databinding.ActivityMainBinding
import com.example.nasaimagegallery.fragments.ImageListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.fragmentFactory =
            PlanetFragmentFactory((applicationContext as App).injector)
        setContentView(binding.root)
        initFragment(savedInstanceState)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.fragmnet_container,
                ImageListFragment::class.java, null
            ).commit()
        }
    }
}
