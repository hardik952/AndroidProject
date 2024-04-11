package com.example.medicsapplication

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class PrefManager(var context: Context) {

    private val PREF_NAME = "com.example.medicsapplication"
    private val PREF_ONBOARDING = "onBoarding"
    private val PREF_LOGIN = "login"

    private var preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    private var editor:Editor = preferences.edit()

    fun updateOnBoardingStatus(status:Boolean){
        editor.putBoolean(PREF_ONBOARDING,status)
        editor.commit()
    }
    fun getOnBoardingStatus():Boolean{
       return preferences.getBoolean(PREF_ONBOARDING,false)
    }

    fun updateLoginStatus(status: Boolean){
        editor.putBoolean(PREF_LOGIN,status)
        editor.commit()
    }
    fun getLoginStatus():Boolean{
        return preferences.getBoolean(PREF_LOGIN,false)
    }
 }