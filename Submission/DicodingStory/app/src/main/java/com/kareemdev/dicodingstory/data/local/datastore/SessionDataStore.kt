package com.kareemdev.dicodingstory.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kareemdev.dicodingstory.data.local.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SessionDataStore private constructor(private val dataStore: DataStore<Preferences>){
    companion object{
        @Volatile
        private var INSTANCE: SessionDataStore? = null
        private val TOKEN_KEY = stringPreferencesKey("token")
        /*private val NAME_KEY = stringPreferencesKey("name")
        private val STATE_KEY = booleanPreferencesKey("state")*/

        fun getInstance(dataStore: DataStore<Preferences>): SessionDataStore{
            return INSTANCE ?: synchronized(this){
                val instance = SessionDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
    suspend fun getToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }.firstOrNull()
    }

    suspend fun saveToken(token: String){
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun removeToken(){
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getRealtimeToken(): Flow<String>{
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }
}