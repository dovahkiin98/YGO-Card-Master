package net.inferno.ygocardmaster.repo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.inferno.ygocardmaster.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpModule {

    @Singleton
    @Provides
    fun okhttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().run {
            // sets the timeout for the entire request. default is 0 ( no timeout )
            callTimeout(0, TimeUnit.SECONDS)
            // sets the timeout for each request stage. default is 10 seconds
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            cache(cache)

            // logs requests and responses
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }

            build()
        }
    }

    @Singleton
    @Provides
    fun cache(
        @ApplicationContext context: Context,
    ): Cache {
        val cacheDir = File(context.cacheDir, "cache")
        val cacheSize = 10L * 1024 * 1024

        return Cache(cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}