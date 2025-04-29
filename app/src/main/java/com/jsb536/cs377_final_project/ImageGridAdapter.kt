package com.jsb536.cs377_final_project
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

interface OnImageClickListener {
    fun onImageClick(imageData: ImageData)
}

class ImageGridAdapter(
    private var imageList: List<ImageData>,
    private val listener: OnImageClickListener
) : RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewItem) //

        fun bind(imageData: ImageData, listener: OnImageClickListener) {
            Glide.with(itemView.context)
                .load(imageData.imageUrlSmall)
                .centerCrop()
                .into(imageView)

            itemView.setOnClickListener {
                listener.onImageClick(imageData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position], listener)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateData(newImageList: List<ImageData>) {
        imageList = newImageList
        notifyDataSetChanged()
    }
}