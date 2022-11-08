package com.rickandmorty.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.rickandmorty.R
import com.rickandmorty.helpers.UserAplication

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsActivityId, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title=getString(R.string.settings)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            setPreferencesFromResource(R.xml.user_settings, rootKey)
            val nightMode= findPreference<SwitchPreference>("nightMode")

            nightMode?.setOnPreferenceChangeListener { preference, newValue ->
                UserAplication.prefs.saveFirstTime(false)
                if(!nightMode.isChecked){
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
                true
            }
        }
    }

}