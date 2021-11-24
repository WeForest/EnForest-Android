package com.gsm.presentation.di

import com.gsm.data.network.service.TestService
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
// Singleton, Provides 쓸때 private 말고 public 으로 해야한다.
object NetworkModule {


    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            //서버로부터의 응답까지의 시간이 읽기 시간 초과보다 크면 요청 실패로 판단한다.
            .readTimeout(100, TimeUnit.SECONDS)
            //서버로 요청을 시작한 후 15초가 지날 때 까지 데이터가 안오면 에러로 판단한다.
            .connectTimeout(100, TimeUnit.SECONDS)

            // 얼마나 빨리 서버로 데이터를 받을 수 있는지 판단한다.
            .writeTimeout(100, TimeUnit.SECONDS)
            .// 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(getLoggingInterceptor())

            .build()

    }


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ec2-3-36-97-97.ap-northeast-2.compute.amazonaws.com:3000/")
            .client(okHttpClient)
            //json 변화기 Factory

            .client(provideHttpClient())

            .addConverterFactory(gsonConverterFactory)
            .build()

    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): TestService {
        return (retrofit.create(TestService::class.java))
    }

    @Provides
    @Singleton
    fun imsIService(retrofit: Retrofit): com.gsm.presentation.TestService {
        return (retrofit.create(com.gsm.presentation.TestService::class.java))
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }




    // 서버로 부터 받아온 데이터 log 찍기
    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


}