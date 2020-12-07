package com.example.nasaimagegallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nasaimagegallery.AppConstant.DATA
import com.example.nasaimagegallery.databinding.FragmentDetailBinding
import com.example.nasaimagegallery.datamodel.PlanetDataModel
import com.example.nasaimagegallery.viewmodel.PlanetViewModel

class ImagePagerFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var planetViewModel: PlanetViewModel

    companion object {
        fun newInstance(
            data: List<PlanetDataModel>,
            viewModel: PlanetViewModel
        ): ImagePagerFragment {
            return ImagePagerFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelableArrayList(DATA, ArrayList(data))
                    }
            }.also {
                it.planetViewModel = viewModel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}
