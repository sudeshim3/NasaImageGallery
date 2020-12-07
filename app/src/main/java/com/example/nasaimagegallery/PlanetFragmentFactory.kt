package com.example.nasaimagegallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.nasaimagegallery.di.Injector
import com.example.nasaimagegallery.fragments.ImageListFragment

class PlanetFragmentFactory(private val injector: Injector) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImageListFragment::class.java.name -> {
                ImageListFragment(injector)
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }
    }
}
