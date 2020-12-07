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

class ImageFragment : Fragment() {
    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_IMAGE_RES = "key.imageRes"
        fun newInstance(drawableRes: String?): ImageFragment {
            val fragment = ImageFragment()
            val argument = Bundle()
            argument.putString(KEY_IMAGE_RES, drawableRes)
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
        val imageRes = requireArguments().getString(KEY_IMAGE_RES)
        binding.detailImageview.transitionName = imageRes.toString()

        Glide.with(this)
            .load(imageRes)
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
}
