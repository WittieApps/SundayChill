package wittie.test.sundaychill.view.detailsscreen

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import wittie.test.sundaychill.R
import wittie.test.sundaychill.domain.IMAGE_URL
import wittie.test.sundaychill.model.MovieDetailRepresentation
import wittie.test.sundaychill.view.mainscreen.ARGS_MOVIE_ID

class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsActivityViewModel by viewModel()

    private val rateTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.detailsRateTextView) }
    private val homePageTextTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.homePageTextTextView) }
    private val textTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.detailsTextTextView) }
    private val imageView: ImageView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.detailsImageView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movieId = intent.extras?.getInt(ARGS_MOVIE_ID)
            ?: throw IllegalArgumentException("Movie Id is required to run this activity")

        viewModel.movieLiveData.observe(this, ::onMovieDownloaded)

        viewModel.loadMovieWithId(movieId)
    }

    private fun onMovieDownloaded(movieRepresentation: MovieDetailRepresentation) {
        with(movieRepresentation) {
            this@DetailsActivity.title = title
            rateTextView.text = vote_average.toString()
            textTextView.text = overview
            homePageTextTextView.text = homepage

            Glide.with(this@DetailsActivity)
                .load(IMAGE_URL + poster_path)
                .centerCrop()
                .placeholder(R.drawable.ic_default_image_16dp)
                .into(imageView)
        }
    }
}