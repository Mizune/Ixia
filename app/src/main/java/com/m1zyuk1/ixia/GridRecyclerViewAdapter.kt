package com.m1zyuk1.ixia

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.m1zyuk1.ixia.model.Post


/**
 * Created by yukian on 2018/03/24.
 */

public class GridRecycleViewAdapter(private val list: List<Post>) : RecyclerView.Adapter<GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.post_griditem, parent, false)
        val viewHolder = GridViewHolder(inflate)
        inflate.setOnClickListener{
            val position = viewHolder.adapterPosition
            val post = list.get(position)
            parent.context.startActivity(DrawingDetailActivity.makeIntent(parent.context,post))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.imageView.setImageURI(Uri.parse(list[position].imagePath))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}