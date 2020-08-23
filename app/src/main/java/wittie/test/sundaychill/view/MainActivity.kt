package wittie.test.sundaychill.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import wittie.test.sundaychill.R
import wittie.test.sundaychill.model.MovieRepresentation


class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    private val recyclerView: RecyclerView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.mainRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = MainRecyclerViewAdapter()

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
        (recyclerView.adapter as MainRecyclerViewAdapter).addMovies(list)
    }

}