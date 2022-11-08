package com.rickandmorty.helpers

import android.content.Context

class Prefs(val context: Context) {
    val storage= context.getSharedPreferences(SHARED_NAME,0)

    fun saveUser(name: String){
        storage.edit().putString(SHARED_USER, name).apply()
    }

    fun saveIsLogged(isLogged: Boolean){
        storage.edit().putBoolean(SHARED_IS_LOGGED, isLogged).apply()
    }

    fun saveFavorites(favorites: String){
        storage.edit().putString(SHARED_FAVORITES, favorites).apply()
    }

    fun saveFirstTime(firstTime: Boolean){
        storage.edit().putBoolean(SHARED_FIRST_TIME, firstTime).apply()
    }

    fun getUser(): String?{
        return storage.getString(SHARED_USER, "")
    }

    fun isLogged(): Boolean?{
        val islogged= storage.getBoolean(SHARED_IS_LOGGED, false)
        return islogged
    }

    fun getFavorites(): String?{
        return storage.getString(SHARED_FAVORITES, "")
    }

    fun isFirstTime(): Boolean?{
        return storage.getBoolean(SHARED_FIRST_TIME, true)
    }

    companion object{
        const val SHARED_NAME= "my"
        const val SHARED_USER= "user"
        const val SHARED_IS_LOGGED= "isLogged"
        const val SHARED_FAVORITES= "favorites"
        const val SHARED_FIRST_TIME= "isFirstTime"
    }
}