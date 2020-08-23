package wittie.test.sundaychill.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import wittie.test.sundaychill.R
import wittie.test.sundaychill.model.MovieRepresentation

class MainListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.movieItemImageView)
    private val titleTextView: TextView = itemView.findViewById(R.id.movieItemTitleTextView)
    private val textTextView: TextView = itemView.findViewById(R.id.movieItemTextTextView)
    private val rateTextView: TextView = itemView.findViewById(R.id.movieItemRateTextView)

    private var id = -1

    init {
        itemView.setOnClickListener {
            Toast.makeText(it.context, "Clicked movie $id", Toast.LENGTH_SHORT).show()
        }
    }

    fun bind(movieRepresentation: MovieRepresentation) {
        with(movieRepresentation) {
            titleTextView.text = title
            textTextView.text = overview
            imageView.tag = poster_path
            rateTextView.text = vote_average.toString()
            this@MainListItemViewHolder.id = id
        }
    }

}