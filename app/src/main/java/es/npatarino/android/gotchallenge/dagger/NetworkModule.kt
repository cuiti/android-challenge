package es.npatarino.android.gotchallenge.dagger

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import es.npatarino.android.gotchallenge.service.GotApiService
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object{
        private const val BASE_URL = "https://project-8424324399725905479.firebaseio.com/"
    }

    @Provides
    @Singleton
    fun provideGotApi(retrofit: Retrofit): GotApiService =
            retrofit.create(GotApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, callAdapterFactory: RxJava2CallAdapterFactory,
                        converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, (5 * 1000 * 1000).toLong()) // 5Mb cache
    }

    @Provides
    @Singleton
    fun provideCacheFile(context: Context): File {
        return File(context.cacheDir, "network_cache")
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}