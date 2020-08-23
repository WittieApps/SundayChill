package wittie.test.sundaychill.domain.injection

import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wittie.test.sundaychill.domain.BASE_URL
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.view.MailActivityViewModel

private val retrofitModule = module {

    fun provideGsonConverterFactory() = GsonConverterFactory.create(GsonBuilder().create())

    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    single { provideGsonConverterFactory() }
    single { provideRetrofit(get()) }
}


private val moviesApiModule = module {

    fun provideMoviesApi(retrofit: Retrofit) =
        retrofit.create(MoviesAPIRetrofitInterface::class.java)

    single { provideMoviesApi(get()) }
}

private val viewModelModule = module {

    viewModel { MailActivityViewModel(get()) }

}

val appModules = listOf(retrofitModule, moviesApiModule, viewModelModule)
