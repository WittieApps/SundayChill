package wittie.test.sundaychill.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.domain.toMovieRepresentation
import wittie.test.sundaychill.model.MovieRepresentation
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
    private val moviesAPIRetrofitInterface: MoviesAPIRetrofitInterface,
    private val backgroundCoroutineContext: CoroutineContext
) : ViewModel() {

    val movieLiveData = MutableLiveData<MovieRepresentation>()

    val movieListLiveData = MutableLiveData<List<MovieRepresentation>>()

    fun loadMovieWithId(id: Int) {
        viewModelScope.launch (backgroundCoroutineContext) {
            val movieById = moviesAPIRetrofitInterface.getMovieById(id).toMovieRepresentation()
            movieLiveData.postValue(movieById)
        }
    }

    fun loadTopRatedMovies() {
        viewModelScope.launch (backgroundCoroutineContext) {
            val topRatedMovies = moviesAPIRetrofitInterface.getTopRatedMovies()
            movieListLiveData.postValue(topRatedMovies.results.map { it.toMovieRepresentation() })
        }
    }
}
