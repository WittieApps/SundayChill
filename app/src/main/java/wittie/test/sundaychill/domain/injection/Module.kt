package wittie.test.sundaychill.domain.injection

import android.content.Context
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wittie.test.sundaychill.domain.AppSharedPreferences
import wittie.test.sundaychill.domain.BASE_URL
import wittie.test.sundaychill.domain.MoviesAPIRetrofitInterface
import wittie.test.sundaychill.view.MainActivityViewModel

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

    viewModel { MainActivityViewModel(get(), get(), Dispatchers.IO) }

}

val preferencesModule = module {

    fun provideSharedPreferences(appContext: Context) = AppSharedPreferences(appContext)

    single { provideSharedPreferences(androidApplication()) }
}

val appModules = listOf(retrofitModule, moviesApiModule, viewModelModule, preferencesModule)
