package com.example.nasaimagegallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    var viewModelFactory: SharedVMFactory? = null
        set(value) {
            if (value != null) {
                field = value
                setupViewModel()
            }
        }
    var data: List<PlanetDataModel> = listOf()
    lateinit var viewModel: PlanetViewModel

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

    fun openPageFragment(planetGridItemBinding: PlanetGridItemBinding) {
    }
}
