package wittie.test.sundaychill.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import wittie.test.sundaychill.R


class MainActivity : AppCompatActivity() {

    private val viewModel: MailActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.movieLiveData.observe(this, { movie ->
            Toast.makeText(
                this,
                "Movie: ${movie.title} successfully downloaded",
                Toast.LENGTH_SHORT
            ).show()
        })

        viewModel.loadMovieWithId(550)
    }

}