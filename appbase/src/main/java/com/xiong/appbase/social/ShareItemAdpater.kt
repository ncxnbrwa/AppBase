package com.myxianwen.share.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myxianwen.share.entity.ShareItem
import com.xiong.appbase.R
import kotlinx.android.synthetic.main.share_item.view.*
import java.util.*

/**
 * Created by iiMedia on 2017/8/19.
 * 分享视图adapter
 */
class ShareItemAdpater(val list: ArrayList<ShareItem>, val itemClickListener: (ShareItem) -> Unit) : RecyclerView.Adapter<ShareItemAdpater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.share_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View, val itemClickListener: (ShareItem) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(item: ShareItem) {
            with(item) {
                itemView.shareIcon.setImageResource(item.iconId)
                itemView.name.text = item.name
                itemView.setOnClickListener { itemClickListener(this) }
            }
        }
    }
}