package com.rickandmorty.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.google.android.material.textfield.TextInputEditText
import com.rickandmorty.R
import com.rickandmorty.helpers.UserAplication.Companion.prefs

class LoginActivity : AppCompatActivity() {

    lateinit var userTextInputEditText: TextInputEditText
    lateinit var passwordTextInputEditText: TextInputEditText
    lateinit var forgotPassword: TextView
    lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userTextInputEditText= findViewById(R.id.activityLoginTextEditUserId)
        passwordTextInputEditText= findViewById(R.id.activityLoginTextEditPasswordId)
        forgotPassword= findViewById(R.id.activityLoginTextViewForgotPasswordId)
        continueButton= findViewById(R.id.activityLoginButtonContinueId)
    }

    override fun onStart() {
        super.onStart()


        val prefsSettings = PreferenceManager.getDefaultSharedPreferences(this)

        if(prefs.isFirstTime()==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            if(prefsSettings.getBoolean("nightMode", true)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        goTo()

        continueButton.setOnClickListener {
            if (userTextInputEditText.text?.isNotEmpty() ==true  ||
                passwordTextInputEditText.text?.isNotEmpty() ==true){
                prefs.saveUser(userTextInputEditText.text.toString())
                prefs.saveIsLogged(true)
                goToMainActivity()
            }
            else{
                Toast.makeText(this, "El usuario o la contraseña estan incompletos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goTo(){
        if(prefs.isLogged()==true){
            goToMainActivity()
        }
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}