package com.m1zyuk1.ixia

import android.databinding.adapters.ViewBindingAdapter.setPadding
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.support.v7.widget.GridLayoutManager
import android.view.View


/**
 * Created by yukian on 2018/03/25.
 */
class GridDecoration(private val sideMargin: Int) : RecyclerView.ItemDecoration() {
    private val halfMargin: Int
    private val halfMinusMargin: Int
    private var initializedRecycler = false

    init {
        halfMargin = sideMargin / 2
        halfMinusMargin = -1 * halfMargin
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                       state: RecyclerView.State) {

        if (parent.layoutManager !is GridLayoutManager) {
            // とりあえずGridLayoutManager以外は無視
            super.getItemOffsets(outRect, view, parent, state)
            return
        }

        setRecyclerAttr(parent)

        val adapterPosition = parent.getChildAdapterPosition(view)
        if (RecyclerView.NO_POSITION == adapterPosition) return

        val lm = parent.layoutManager as GridLayoutManager
        val ssl = lm.spanSizeLookup

        val spanCount = lm.spanCount

        if (ssl.getSpanSize(adapterPosition) >= spanCount) {
            // 全幅以上の場合は余白をとらないようにマイナスマージンをつける
            val params = view.getLayoutParams() as ViewGroup.MarginLayoutParams
            params.setMargins(halfMinusMargin, params.topMargin, halfMinusMargin, sideMargin)
            view.setLayoutParams(params)
            super.getItemOffsets(outRect, view, parent, state)
            return
        }

        outRect.left = halfMargin
        outRect.right = halfMargin
        // 見やすさのためにbottomにも設定
        outRect.bottom = sideMargin
    }

    private fun setRecyclerAttr(parent: RecyclerView) {
        // Decoration内で設定するものでもないが、これ単体で完結させるためにとりあえずここに記載
        if (initializedRecycler) return
        initializedRecycler = true
        parent.clipToPadding = false
        parent.setPadding(
                halfMargin, parent.paddingTop, halfMargin, parent.paddingBottom
        )
    }
}

