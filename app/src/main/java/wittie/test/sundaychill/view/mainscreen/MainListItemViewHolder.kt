package wittie.test.sundaychill.view.mainscreen

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import wittie.test.sundaychill.R
import wittie.test.sundaychill.domain.IMAGE_URL
import wittie.test.sundaychill.model.MovieRepresentation
import wittie.test.sundaychill.view.OnListItemClickedInterface

class MainListItemViewHolder(
    itemView: View,
    private val onListItemClickedInterface: OnListItemClickedInterface
) : RecyclerView.ViewHolder(itemView) {

    private val movieItemConstraintLayout: ViewGroup =
        itemView.findViewById(R.id.movieItemConstraintLayout)
    private val imageView: ImageView = itemView.findViewById(R.id.movieItemImageView)
    private val titleTextView: TextView = itemView.findViewById(R.id.movieItemTitleTextView)
    private val textTextView: TextView = itemView.findViewById(R.id.movieItemTextTextView)
    private val rateTextView: TextView = itemView.findViewById(R.id.movieItemRateTextView)

    private var id = -1

    init {
        movieItemConstraintLayout.setOnClickListener {
            onListItemClickedInterface.onListItemClicked(
                id
            )
        }
    }

    fun bind(movieRepresentation: MovieRepresentation) {
        with(movieRepresentation) {
            this@MainListItemViewHolder.id = id
            titleTextView.text = title
            textTextView.text = overview
            rateTextView.text = vote_average.toString()

            Glide.with(this@MainListItemViewHolder.itemView.context)
                .load(IMAGE_URL + poster_path)
                .centerCrop()
                .placeholder(R.drawable.ic_default_image_16dp)
                .into(imageView)
        }
    }

}