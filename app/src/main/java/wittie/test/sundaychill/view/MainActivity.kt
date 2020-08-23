package wittie.test.sundaychill.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import wittie.test.sundaychill.R
import wittie.test.sundaychill.model.MovieRepresentation
import kotlin.LazyThreadSafetyMode.NONE


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        val seeBestMenuItem = menu.findItem(R.id.action_see_best)
        seeBestMenuItem.isChecked = viewModel.isSeeBestChecked
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_see_best) {
            item.isChecked = !item.isChecked
            viewModel.isSeeBestChecked = item.isChecked
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}