package wittie.test.sundaychill.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wittie.test.sundaychill.domain.BASE_URL
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.domain.toMovieRepresentation
import wittie.test.sundaychill.model.MovieRepresentation

class MailActivityViewModel : ViewModel() {

    val movieLiveData = MutableLiveData<MovieRepresentation>()

    fun loadMovieWithId(id: Int) {
        viewModelScope.launch {
            val movieById = runGetMovieById(id)
            movieLiveData.postValue(movieById)
        }
    }

    private suspend fun runGetMovieById(id: Int): MovieRepresentation {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val moviesAPIRetrofitInterface = retrofit.create(MoviesAPIRetrofitInterface::class.java)

        return moviesAPIRetrofitInterface.getMovieById(id).toMovieRepresentation()
    }
}
