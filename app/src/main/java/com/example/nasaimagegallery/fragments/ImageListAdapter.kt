package com.example.nasaimagegallery.fragments

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nasaimagegallery.ViewHolderListener
import com.example.nasaimagegallery.ViewHolderListenerImpl
import com.example.nasaimagegallery.databinding.PlanetGridItemBinding
import com.example.nasaimagegallery.datamodel.PlanetDataModel
import com.example.nasaimagegallery.viewmodel.PlanetViewModel

class ImageListAdapter(val imageListFragment: ImageListFragment, val viewModel: PlanetViewModel) :
    RecyclerView.Adapter<ImageListAdapter.ImageThumbnailViewHolder>() {

    var planetsList = listOf<PlanetDataModel>()
    val requestManager = Glide.with(imageListFragment)
    private val viewHolderListener: ViewHolderListener = ViewHolderListenerImpl(imageListFragment, viewModel)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageThumbnailViewHolder {
        val binding =
            PlanetGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageThumbnailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageThumbnailViewHolder, position: Int) {
        holder.bind(planetsList[position])
    }

    override fun getItemCount() = planetsList.size

    fun setData(data: List<PlanetDataModel>) {
        this.planetsList = data
        notifyDataSetChanged()
    }

    inner class ImageThumbnailViewHolder(val planetGridItemBinding: PlanetGridItemBinding) :
        RecyclerView.ViewHolder(planetGridItemBinding.root), View.OnClickListener {

        init {
            planetGridItemBinding.root.setOnClickListener {
                viewModel.viewpagerCurrentPosition = adapterPosition
                imageListFragment.openPageFragment(planetGridItemBinding)
            }
        }

        fun bind(planetDataModel: PlanetDataModel) {
            planetGridItemBinding.titleTextView.text = planetDataModel.title
            setImage(planetDataModel.imageUrl)
            planetGridItemBinding.planetThumbnailImageView.transitionName = planetDataModel.imageUrl
        }

        private fun setImage(planetImageUrl: String) {
            requestManager
                .load(planetImageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(planetGridItemBinding.planetThumbnailImageView)
        }

        override fun onClick(view: View) {
            viewHolderListener.onItemClicked(view, adapterPosition)
        }
    }
}
