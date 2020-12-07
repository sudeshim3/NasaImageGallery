package com.example.nasaimagegallery

import android.transition.TransitionSet
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.nasaimagegallery.viewmodel.PlanetViewModel
import java.util.concurrent.atomic.AtomicBoolean

class ViewHolderListenerImpl(private val fragment: Fragment, val viewModel: PlanetViewModel) :
    ViewHolderListener {
    private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()

    override fun onLoadCompleted(view: ImageView, adapterPosition: Int) {
        // StartPostponedEnterTransition is called at the end so that selected image is loaded after completion.
        if (viewModel.viewpagerCurrentPosition != adapterPosition) {
            return
        }
        if (enterTransitionStarted.getAndSet(true)) {
            return
        }
        fragment.startPostponedEnterTransition()
    }

    override fun onItemClicked(view: View, adapterPosition: Int) {
        viewModel.viewpagerCurrentPosition = adapterPosition

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        (fragment.exitTransition as TransitionSet).excludeTarget(view, true)
        val transitioningView = view.findViewById<ImageView>(R.id.planet_thumbnail_imageView)
        fragment.parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true) // Optimize for shared element transition
            .addSharedElement(transitioningView, transitioningView.transitionName)
            .replace(
                R.id.fragmnet_container,
                ImagePagerFragment.newInstance(viewModel.planetList, viewModel),
                ImagePagerFragment::class.java
                    .simpleName
            )
            .addToBackStack(null)
            .commit()
    }
}
