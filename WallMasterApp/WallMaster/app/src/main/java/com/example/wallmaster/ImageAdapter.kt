package com.example.wallmaster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallmaster.databinding.GroupImgWallBinding

class ImageAdapter(private val onItemClick: (Image) -> Unit):
    RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    private val wallList = ArrayList<Image>()

    class ImageHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = GroupImgWallBinding.bind(item)
        fun bind(image: Image) = with(binding){
            imgWall.setImageBitmap(image.imgId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_img_wall, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return wallList.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(wallList[position])

        holder.itemView.setOnClickListener {
            onItemClick(wallList[position])
        }
    }

    fun addWall(image: Image) {
        wallList.add(image)
        notifyDataSetChanged()
    }

    fun clearWalls() {
        wallList.clear()
        notifyDataSetChanged()
    }
}