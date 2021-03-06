package com.gsm.presentation.di

import com.gsm.data.network.service.GroupService
import com.gsm.data.network.service.MissionService
import com.gsm.data.network.service.ProfileService
import com.gsm.data.network.service.TestService
import com.gsm.data.network.service.sign.LoginService
import com.gsm.presentaBroadcastReceivertion.data.AiService
import com.gsm.presentation.util.Constant.Companion.AI_SERVER
import com.gsm.presentation.util.Constant.Companion.Local_SERVER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
            .readTimeout(30, TimeUnit.SECONDS)
            //서버로 요청을 시작한 후 15초가 지날 때 까지 데이터가 안오면 에러로 판단한다.
            .connectTimeout(30, TimeUnit.SECONDS)

            // 얼마나 빨리 서버로 데이터를 받을 수 있는지 판단한다.
            .writeTimeout(30, TimeUnit.SECONDS)
            .// 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(getLoggingInterceptor())


            .build()

    }


    @Singleton
    @Provides
    @Named("ai")
    fun provideRetrofitAiInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AI_SERVER)
            .client(okHttpClient)
            //json 변화기 Factory

            .client(provideHttpClient())

            .addConverterFactory(gsonConverterFactory)
            .build()

    }


    @Singleton
    @Provides
    @Named("main")
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Local_SERVER)
            .client(okHttpClient)
            //json 변화기 Factory

            .client(provideHttpClient())

            .addConverterFactory(gsonConverterFactory)
            .build()

    }
//

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideMissionService(@Named("main")retrofit: Retrofit): MissionService {
        return (retrofit.create(MissionService::class.java))
    }

    @Provides
    fun provideAiService(@Named("ai") retrofit: Retrofit): AiService {
        return (retrofit.create(AiService::class.java))
    }


    @Provides
    @Singleton
    fun provideLoginService(@Named("main") retrofit: Retrofit): LoginService {
        return (retrofit.create(LoginService::class.java))
    }

    @Provides
    @Singleton
    fun imsIService(@Named("main") retrofit: Retrofit): com.gsm.presentation.data.TestService {
        return (retrofit.create(com.gsm.presentation.data.TestService::class.java))
    }


    @Provides
    @Singleton
    fun getUserInfo(@Named("main") retrofit: Retrofit): com.gsm.presentation.ui.userinfo.api.UserInfoService {
        return (retrofit.create(com.gsm.presentation.ui.userinfo.api.UserInfoService::class.java))
    }

    @Provides
    @Singleton
    fun testService(@Named("main") retrofit: Retrofit): TestService {
        return (retrofit.create(TestService::class.java))
    }

    @Provides
    @Singleton
    fun provideGroupService(@Named("main") retrofit: Retrofit): GroupService {
        return (retrofit.create(GroupService::class.java))
    }

    @Provides
    @Singleton
    fun provideProfileService(@Named("main") retrofit: Retrofit): ProfileService {
        return (retrofit.create(ProfileService::class.java))
    }


    // 서버로 부터 받아온 데이터 log 찍기
    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

}


