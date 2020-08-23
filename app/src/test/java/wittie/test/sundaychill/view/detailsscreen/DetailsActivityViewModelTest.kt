package wittie.test.sundaychill.view.detailsscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import wittie.test.sundaychill.CoroutinesTestRule
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.model.MovieResponse

@Suppress("MemberVisibilityCanBePrivate")
@ExperimentalCoroutinesApi
class DetailsActivityViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutinesTestRule = CoroutinesTestRule()

    val moviesAPIRetrofitInterfaceMock: MoviesAPIRetrofitInterface = mock()

    val viewModel = DetailsActivityViewModel(
        moviesAPIRetrofitInterfaceMock,
        coroutinesTestRule.coroutineDispatcher
    )

    @Test
    fun `loadMovieWithId posts live data successfully`() = runBlockingTest {
        val selectedMovieId = 42
        val expectedResponse = createTestMovieResponse()
        whenever(moviesAPIRetrofitInterfaceMock.getMovieById(selectedMovieId)).thenReturn(
            expectedResponse
        )

        viewModel.loadMovieWithId(selectedMovieId)

        verify(moviesAPIRetrofitInterfaceMock).getMovieById(selectedMovieId)
        assertEquals(expectedResponse.id, viewModel.movieLiveData.value?.id)
        assertEquals(expectedResponse.title, viewModel.movieLiveData.value?.title)
        assertEquals(expectedResponse.homepage, viewModel.movieLiveData.value?.homepage)
    }


    fun createTestMovieResponse() = MovieResponse(
        -1,
        "homepage",
        "originalLanguage",
        "title",
        "overview",
        0.1f,
        "posterPath"
    )
}