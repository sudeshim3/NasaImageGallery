package com.example.nasaimagegallery

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.nasaimagegallery.AppConstant.DATA
import com.example.nasaimagegallery.databinding.FragmentDetailBinding
import com.example.nasaimagegallery.datamodel.PlanetDataModel
import com.example.nasaimagegallery.viewmodel.PlanetViewModel

class ImagePagerFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var data: List<PlanetDataModel>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromArgumentsData()
        with(binding.fragmentDetailsViewpager) {
            adapter = ImageDetailsAdapter(this@ImagePagerFragment, data)
            currentItem = planetViewModel.viewpagerCurrentPosition
            addOnPageChangeListener(object :
                    ViewPager.SimpleOnPageChangeListener() {
                    override fun onPageSelected(position: Int) {
                        planetViewModel.viewpagerCurrentPosition = position
                    }
                })
        }
        prepareSharedElementTransition()

        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
    }

    private fun getDataFromArgumentsData() {
        if (arguments == null || !requireArguments().containsKey(DATA)) {
            throw IllegalArgumentException("missing Arguments. Use newInstance method.")
        }
        data = requireArguments().getParcelableArrayList(DATA)!!
    }

    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        // A similar mapping is set at the ImageListFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    // The method will simply return the fragment at the position and will
                    // not create a new one.
                    val fragment: Fragment =
                        binding.fragmentDetailsViewpager.adapter?.instantiateItem(
                            binding.fragmentDetailsViewpager,
                            planetViewModel.viewpagerCurrentPosition
                        ) as Fragment
                    val view = fragment.view ?: return
                    // Map the first shared element name to the child ImageView.
                    sharedElements[names[0]] = view.findViewById(R.id.detail_imageview)
                }
            })
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
