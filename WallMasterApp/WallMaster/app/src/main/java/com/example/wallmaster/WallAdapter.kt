package com.example.wallmaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallmaster.databinding.WallItemBinding

class WallAdapter(private val onItemClick: (Wall) -> Unit): RecyclerView.Adapter<WallAdapter.WallHolder>() {
    private val wallList = ArrayList<Wall>()
    class WallHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = WallItemBinding.bind(itemView)
        fun bind(wall: Wall) = with(binding) {
            imgWall.setImageBitmap(wall.imgId)
            tvTitle.text = wall.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wall_item, parent, false)
        return WallHolder(view)
    }

    override fun getItemCount(): Int {
        return wallList.size
    }

    override fun onBindViewHolder(holder: WallHolder, position: Int) {
        holder.bind(wallList[position])

        holder.itemView.setOnClickListener {
            onItemClick(wallList[position])
        }
    }

    fun addWall(wall: Wall) {
        wallList.add(wall)
        notifyDataSetChanged()
    }

}