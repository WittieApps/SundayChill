package wittie.test.sundaychill.view.mainscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import wittie.test.sundaychill.CoroutinesTestRule
import wittie.test.sundaychill.domain.AppSharedPreferences
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.model.ListOfMoviesResponse
import wittie.test.sundaychill.model.MovieRepresentation
import wittie.test.sundaychill.model.MovieResponse

@Suppress("MemberVisibilityCanBePrivate")
@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutinesTestRule = CoroutinesTestRule()

    val moviesAPIRetrofitInterfaceMock: MoviesAPIRetrofitInterface = mock()
    val appSharedPreferences: AppSharedPreferences = mock {
        on { isSeeBestChecked } doReturn false
    }

    val viewModel = MainActivityViewModel(
        moviesAPIRetrofitInterfaceMock,
        appSharedPreferences,
        coroutinesTestRule.coroutineDispatcher
    )

    @Test
    fun `isSeeBestChecked set to true repost filtered List`() = runBlockingTest {
        val movie1 = mock<MovieRepresentation> { on { title } doReturn "Harry Potter" }
        val movie2 = mock<MovieRepresentation> { on { title } doReturn "Narnia" }
        val movie3 = mock<MovieRepresentation> { on { title } doReturn "The Lord of the Rings" }
        viewModel.movieListLiveData.value = listOf(movie1, movie2, movie3)

        appSharedPreferences.stub { on { isSeeBestChecked } doReturn false }

        viewModel.isSeeBestChecked = true

        Assert.assertEquals(1, viewModel.movieListLiveData.value?.size)
    }

    @Test
    fun `isSeeBestChecked set to false reloads List`() = runBlockingTest {
        val movie1 = createTestMovieResponse("Harry Potter")
        val movie2 = createTestMovieResponse("Narnia Potter")
        val movie3 = createTestMovieResponse( "The Lord of the Rings")
        val list = mock<ListOfMoviesResponse> {
            on { results } doReturn listOf(movie1, movie2, movie3)
        }

        appSharedPreferences.stub { on { isSeeBestChecked } doReturn true }
        whenever(moviesAPIRetrofitInterfaceMock.getTopRatedMovies()).thenReturn(list)

        viewModel.isSeeBestChecked = false

        Assert.assertEquals(3, viewModel.movieListLiveData.value?.size)
        Assert.assertEquals(movie1.title, viewModel.movieListLiveData.value?.get(0)?.title)
        Assert.assertEquals(movie2.title, viewModel.movieListLiveData.value?.get(1)?.title)
        Assert.assertEquals(movie3.title, viewModel.movieListLiveData.value?.get(2)?.title)
    }

    fun createTestMovieResponse(title: String) = MovieResponse(
        -1,
        "homepage",
        "originalLanguage",
        title,
        "overview",
        0.1f,
        "posterPath"
    )
}