package wittie.test.sundaychill.view.detailsscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.model.MovieDetailRepresentation
import kotlin.coroutines.CoroutineContext

class DetailsActivityViewModel(
    private val moviesAPIRetrofitInterface: MoviesAPIRetrofitInterface,
    private val backgroundCoroutineContext: CoroutineContext
) : ViewModel() {

    val movieLiveData = MutableLiveData<MovieDetailRepresentation>()

    fun loadMovieWithId(id: Int) {
        viewModelScope.launch(backgroundCoroutineContext) {
            val movieById = moviesAPIRetrofitInterface.getMovieById(id)
            movieLiveData.postValue(movieById)
        }
    }
}
