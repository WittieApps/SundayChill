package wittie.test.sundaychill.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import wittie.test.sundaychill.R
import wittie.test.sundaychill.model.MovieRepresentation
import kotlin.LazyThreadSafetyMode.*


class MainActivity : AppCompatActivity(), OnListItemClickedInterface {

    private val viewModel: MainActivityViewModel by viewModel()

    private val mainSwipeRefreshLayout: SwipeRefreshLayout by lazy(NONE) { findViewById(R.id.mainSwipeRefreshLayout) }
    private val recyclerView: RecyclerView by lazy(NONE) { findViewById(R.id.mainRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = MainRecyclerViewAdapter(this)

        mainSwipeRefreshLayout.setOnRefreshListener(viewModel::loadTopRatedMovies)

        viewModel.movieLiveData.observe(this, { movie ->
            Toast.makeText(
                this,
                "Movie: ${movie.title} successfully downloaded",
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.movieListLiveData.observe(this, ::onListOfMoviesDownloaded)

        //viewModel.loadMovieWithId(550)
        viewModel.loadTopRatedMovies()
    }

    private fun onListOfMoviesDownloaded(list: List<MovieRepresentation>) {
        mainSwipeRefreshLayout.isRefreshing = false
        (recyclerView.adapter as MainRecyclerViewAdapter).addMovies(list)
    }

    override fun onListItemClicked(movieId: Int) {
        Toast.makeText(this, "Clicked movie $movieId", Toast.LENGTH_SHORT).show()
    }

}