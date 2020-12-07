package com.example.nasaimagegallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasaimagegallery.datamodel.PlanetDataModel

class ImageDetailsAdapter(
    val fragment: Fragment,
    val data: List<PlanetDataModel>
) : FragmentStatePagerAdapter(
    fragment.childFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount() = data.size

    override fun getItem(position: Int) = ImageFragment.newInstance(data[position].imageUrl)
}
