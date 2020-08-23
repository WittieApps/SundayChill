package wittie.test.sundaychill.view.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import wittie.test.sundaychill.R
import wittie.test.sundaychill.model.MovieRepresentation
import wittie.test.sundaychill.view.OnListItemClickedInterface
import wittie.test.sundaychill.view.detailsscreen.DetailsActivity
import kotlin.LazyThreadSafetyMode.NONE

const val ARGS_MOVIE_ID = "ARGS_MOVIE_ID"

class MainActivity : AppCompatActivity(), OnListItemClickedInterface {

    private val viewModel: MainActivityViewModel by viewModel()

    private val mainSwipeRefreshLayout: SwipeRefreshLayout by lazy(NONE) { findViewById(R.id.mainSwipeRefreshLayout) }
    private val recyclerView: RecyclerView by lazy(NONE) { findViewById(R.id.mainRecyclerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = MainRecyclerViewAdapter(this)

        mainSwipeRefreshLayout.setOnRefreshListener(viewModel::loadTopRatedMovies)

        viewModel.movieListLiveData.observe(this, ::onListOfMoviesDownloaded)

        viewModel.loadTopRatedMovies()
    }

    private fun onListOfMoviesDownloaded(list: List<MovieRepresentation>) {
        mainSwipeRefreshLayout.isRefreshing = false
        (recyclerView.adapter as MainRecyclerViewAdapter).addMovies(list)
    }

    override fun onListItemClicked(movieId: Int) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(ARGS_MOVIE_ID, movieId)
        })
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