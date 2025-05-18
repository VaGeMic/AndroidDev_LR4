package com.example.lr3.di

import com.example.lr3.data.remote.CityService
import com.example.lr3.data.remote.WeatherService
import com.example.lr3.data.repository.WeatherRepository
import com.example.lr3.domain.usecase.GetCityCoordinatesUseCase
import com.example.lr3.domain.usecase.GetDailyForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides @Singleton @JvmStatic
        fun provideLogging(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        @Provides @JvmStatic
        fun provideClient(log: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder().addInterceptor(log).build()

        @Provides @Singleton @JvmStatic
        fun provideCityService(client: OkHttpClient): CityService =
            Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CityService::class.java)

        @Provides @Singleton @JvmStatic
        fun provideWeatherService(client: OkHttpClient): WeatherService =
            Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)

        @Provides @Singleton @JvmStatic
        fun provideRepository(
            cityService: CityService,
            weatherService: WeatherService
        ): WeatherRepository =
            WeatherRepository(cityService, weatherService)

        @Provides @JvmStatic
        fun provideGetCityUseCase(r: WeatherRepository) =
            GetCityCoordinatesUseCase(r)

        @Provides @JvmStatic
        fun provideGetDailyUseCase(r: WeatherRepository) =
            GetDailyForecastUseCase(r)
    }
}
