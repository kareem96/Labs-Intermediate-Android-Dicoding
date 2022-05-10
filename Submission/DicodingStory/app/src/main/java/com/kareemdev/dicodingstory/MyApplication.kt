package com.kareemdev.dicodingstory

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kareemdev.dicodingstory.data.local.database.StoryDatabase
import com.kareemdev.dicodingstory.data.local.datastore.SessionDataStore
import com.kareemdev.dicodingstory.data.remote.ApiConfig
import com.kareemdev.dicodingstory.domain.repository.AuthRepository
import com.kareemdev.dicodingstory.domain.repository.Repository
import com.kareemdev.dicodingstory.domain.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MyApplication: Application() {
    private val viewModelModule = module{
        viewModel{
            SessionViewModel(get())
        }
        viewModel<StoryViewModel>{
            StoryViewModelImpl(get(), get())
        }
        viewModel<AuthViewModel>{
            AuthViewModelImpl(get())
        }
    }

    private val apiModule = module{
        single { ApiConfig.getApiService(get()) }
    }

    private val databaseModule = module{
        single { StoryDatabase.getInstance(applicationContext) }
    }

    private val preferenceModule = module{
        single { SessionDataStore.getInstance(dataStore) }
    }

    private val repositoryModule = module{
        single { AuthRepository(get()) }
        single { Repository(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    apiModule,
                    viewModelModule,
                    preferenceModule,
                )
            )
        }
    }
}