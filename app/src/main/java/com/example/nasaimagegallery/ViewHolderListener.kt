package com.example.nasaimagegallery

import android.view.View
import android.widget.ImageView

interface ViewHolderListener {
    fun onLoadCompleted(view: ImageView, adapterPosition: Int)
    fun onItemClicked(view: View, adapterPosition: Int)
}
