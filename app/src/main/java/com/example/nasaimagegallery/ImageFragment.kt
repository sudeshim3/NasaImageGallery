package com.example.nasaimagegallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
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
    var scrollState = true
    val MIN_LINES = 2
    val MAX_LINES = 50

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

        loadImage(planetData.imageUrl)
        addTransitionListener()
        binding.scrollRegion.setOnTouchListener { view, motionEvent ->
            scrollState
        }
        return binding.root
    }

    private fun enableScroll(shouldScroll: Boolean) {
        scrollState = shouldScroll
    }

    private fun addTransitionListener() {
        binding.root.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, state: Int) {
                when (motionLayout.currentState) {
                    motionLayout.startState -> {
                        enableScroll(true)
                        binding.imageDescriptionTextView.maxLines = MIN_LINES
                    }
                    motionLayout.endState -> {
                        enableScroll(false)
                        binding.imageDescriptionTextView.maxLines = MAX_LINES
                    }
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
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
