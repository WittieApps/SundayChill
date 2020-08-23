package wittie.test.sundaychill.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.domain.toMovieRepresentation
import wittie.test.sundaychill.model.MovieRepresentation

class MailActivityViewModel(
    private val moviesAPIRetrofitInterface: MoviesAPIRetrofitInterface
) : ViewModel() {

    val movieLiveData = MutableLiveData<MovieRepresentation>()

    fun loadMovieWithId(id: Int) {
        viewModelScope.launch {
            val movieById = runGetMovieById(id)
            movieLiveData.postValue(movieById)
        }
    }

    private suspend fun runGetMovieById(id: Int): MovieRepresentation {
        return moviesAPIRetrofitInterface.getMovieById(id).toMovieRepresentation()
    }
}
