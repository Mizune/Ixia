package com.m1zyuk1.ixia

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView



class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView

    init {
        imageView = itemView.findViewById(R.id.image_view)
    }
}