package com.example.nasaimagegallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nasaimagegallery.databinding.FragmentImageDetailBinding
import com.example.nasaimagegallery.datamodel.PlanetDataModel

class ImageFragment : Fragment() {
    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val PLANET_DATA = "planetData"
        fun newInstance(planetData: PlanetDataModel): ImageFragment {
            val fragment = ImageFragment()
            val argument = Bundle()
            argument.putParcelable(PLANET_DATA, planetData)
            fragment.arguments = argument
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        if (arguments == null || requireArguments().containsKey(PLANET_DATA).not()) {
            requireActivity().supportFragmentManager.popBackStack()
        }
        val planetData: PlanetDataModel = requireArguments().getParcelable(PLANET_DATA)!!
        setPlanetData(planetData)

        Glide.with(this)
            .load(planetData.imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    requireParentFragment().startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    requireParentFragment().startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.detailImageview)
        return binding.root
    }

    private fun setPlanetData(planetData: PlanetDataModel) {
        with(binding) {
            detailImageview.transitionName = planetData.imageUrl
            imageDescriptionTextView.text = planetData.imageDetail
            smallImageNameTextView.text = planetData.title
            imageNameTextView.text = planetData.title
            copyrightTextView.text = planetData.copyright
            dateTextView.text = planetData.date
        }
    }
}
