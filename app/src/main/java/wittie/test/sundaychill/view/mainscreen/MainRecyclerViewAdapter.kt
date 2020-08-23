package wittie.test.sundaychill.view.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import wittie.test.sundaychill.R
import wittie.test.sundaychill.model.MovieRepresentation
import wittie.test.sundaychill.view.OnListItemClickedInterface

class MainRecyclerViewAdapter(private val onListItemClickedInterface: OnListItemClickedInterface)
    : RecyclerView.Adapter<MainListItemViewHolder>() {

    private var listOfMovies = mutableListOf<MovieRepresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainListItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false),
        onListItemClickedInterface
    )

    override fun onBindViewHolder(holder: MainListItemViewHolder, position: Int) {
        holder.bind(listOfMovies[position])
    }

    override fun getItemCount() = listOfMovies.size

    fun addMovies(list: List<MovieRepresentation>) {
        listOfMovies = list.toMutableList()
        notifyDataSetChanged()
    }
}

