package wittie.test.sundaychill.view.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wittie.test.sundaychill.domain.AppSharedPreferences
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.domain.toMovieRepresentation
import wittie.test.sundaychill.model.MovieRepresentation
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
    private val moviesAPIRetrofitInterface: MoviesAPIRetrofitInterface,
    private val sharedPreferences: AppSharedPreferences,
    private val backgroundCoroutineContext: CoroutineContext
) : ViewModel() {

    val movieLiveData = MutableLiveData<MovieRepresentation>()

    val movieListLiveData = MutableLiveData<List<MovieRepresentation>>()

    var isSeeBestChecked: Boolean
        get() = sharedPreferences.isSeeBestChecked
        set(value) {
            sharedPreferences.isSeeBestChecked = value
            if (value) {
                postFilteredList()
            } else {
                loadTopRatedMovies()
            }
        }

    private fun postFilteredList() {
        movieListLiveData.postValue(movieListLiveData.value?.filter {
            it.title.contains("Lord of the Rings")
        })
    }

    fun loadTopRatedMovies() {
        viewModelScope.launch(backgroundCoroutineContext) {
            val topRatedMovies = moviesAPIRetrofitInterface.getTopRatedMovies()
            movieListLiveData.postValue(topRatedMovies.results.map { it.toMovieRepresentation() })
        }
    }
}
