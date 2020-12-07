package com.example.nasaimagegallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaimagegallery.databinding.FragmentImagelistBinding
import com.example.nasaimagegallery.di.Injector
import com.example.nasaimagegallery.viewmodel.SharedVMFactory

class ImageListFragment(private val injector: Injector) : Fragment() {

    private var _binding: FragmentImagelistBinding? = null
    private val binding get() = _binding!!
    var viewModelFactory: SharedVMFactory? = null

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
    }
}
