package ru.nowandroid.youtube.nowjsoupandroid.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*
import ru.nowandroid.youtube.nowjsoupandroid.CropSquareTransformation

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.row_tv_title
    private val description: TextView = itemView.row_tv_description
    private val image: ImageView = itemView.row_img
    private val additionalInfo: TextView = itemView.row_tv_additional_info
    private var link = ""

    init {
        itemView.setOnClickListener {
            itemView
                .findNavController()
                .navigate(ListNewsFragmentDirections.actionListNewsFragmentToDetailsNewsFragment().setLink(link))
        }
    }

    fun bind(news: News) {
        title.text = news.title
        description.text = news.description
        Picasso.with(itemView.context)
            .load(news.linkImage)
            .transform(CropSquareTransformation())
            .into(image)
        additionalInfo.text = news.additionalInfo
        link = news.linkDetails
    }
}