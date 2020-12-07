package com.example.nasaimagegallery.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nasaimagegallery.ImagePagerFragment
import com.example.nasaimagegallery.R
import com.example.nasaimagegallery.databinding.FragmentImagelistBinding
import com.example.nasaimagegallery.databinding.PlanetGridItemBinding
import com.example.nasaimagegallery.datamodel.PlanetData
import com.example.nasaimagegallery.datamodel.PlanetDataModel
import com.example.nasaimagegallery.di.Injector
import com.example.nasaimagegallery.viewmodel.PlanetViewModel
import com.example.nasaimagegallery.viewmodel.SharedVMFactory
import com.google.android.material.snackbar.Snackbar

class ImageListFragment(private val injector: Injector) : Fragment() {

    private var _binding: FragmentImagelistBinding? = null
    private val binding get() = _binding!!
    lateinit var imageListAdapter: ImageListAdapter
    lateinit var viewModel: PlanetViewModel
    var viewModelFactory: SharedVMFactory? = null
        set(value) {
            if (value != null) {
                field = value
                setupViewModel()
            }
        }
    var data: List<PlanetDataModel> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagelistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injector.inject(this)
        setupViewModel() // Remove the dependency from here
        binding.recyclerView.adapter = imageListAdapter
        prepareTransition()
        postponeEnterTransition()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is PlanetData.Empty -> {
                        showEmptyContent()
                    }
                    is PlanetData.Error -> {
                        showError(it.exception)
                    }
                    is PlanetData.Result -> {
                        data = it.data
                        viewModel.planetList = data
                        imageListAdapter.setData(it.data)
                    }
                }
            }
        )
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory!!
            ).get(PlanetViewModel::class.java)
    }

    private fun showEmptyContent() {
        Snackbar.make(binding.root, getString(R.string.no_image_found), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showError(exception: Exception) {
        when (exception) {
            is IllegalArgumentException -> {
                Snackbar.make(
                    binding.root,
                    getString(R.string.some_error_occured),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    fun openPageFragment(binding: PlanetGridItemBinding) {
        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        (exitTransition as TransitionSet).excludeTarget(binding.root, true)
        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true) // Optimize for shared element transition
            .addSharedElement(
                binding.planetThumbnailImageView,
                binding.planetThumbnailImageView.transitionName
            )
            .replace(
                R.id.fragmnet_container,
                ImagePagerFragment.newInstance(viewModel.planetList, viewModel),
                ImagePagerFragment::class.java
                    .simpleName
            )
            .addToBackStack(null)
            .commit()
    }

    private fun prepareTransition() {
        exitTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.grid_exit_transition)

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>,
                sharedElements: MutableMap<String, View>
            ) {
                val planetViewHolder =
                    binding.recyclerView.findViewHolderForAdapterPosition(viewModel.viewpagerCurrentPosition)
                        ?: return
                sharedElements.put(
                    names[0],
                    planetViewHolder.itemView.findViewById(R.id.planet_thumbnail_imageView)
                )
            }
        })
    }
}
