package com.example.nasaimagegallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaimagegallery.databinding.FragmentImageDetailBinding

class ImageFragment : Fragment() {
    var _binding: FragmentImageDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}
