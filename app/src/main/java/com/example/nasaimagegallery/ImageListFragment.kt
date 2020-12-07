package com.example.nasaimagegallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaimagegallery.databinding.FragmentImagelistBinding
import com.example.nasaimagegallery.viewmodel.SharedVMFactory

class ImageListFragment : Fragment() {

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
}
