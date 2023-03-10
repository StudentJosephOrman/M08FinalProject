package com.example.m08finalproject

import android.content.Intent
import android.icu.text.IDNA.Info
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import com.example.m08finalproject.MainActivity.Companion.TTSLang
import com.example.m08finalproject.MainActivity.Companion.TTSSpeed
import com.example.m08finalproject.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.dwLayout
import org.intellij.lang.annotations.Language
import java.util.Locale

class Settings : AppCompatActivity() {
    lateinit var myToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toggleButton.setOnClickListener{
            if(toggleButton.isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            toggleButton.isChecked = true
        }

        when (TTSSpeed) {
            0.5f -> {
                txtWPM.text = ".5x Spd"
                sbSpeed.setProgress(0)
            }
            1.0f -> {
                txtWPM.text = "1x Spd"
            }
            2.0f -> {
                txtWPM.text = "2x Spd"
                sbSpeed.setProgress(2)
            }
        }

        when (TTSLang) {
            Locale.ENGLISH -> {
                dpAILanguage.setSelection(0)
            }
            Locale.GERMAN -> {
                dpAILanguage.setSelection(1)
            }
            Locale.ITALIAN -> {
                dpAILanguage.setSelection(2)
            }
            Locale.FRENCH -> {
                dpAILanguage.setSelection(3)
            }
        }

        btnSubmitSettings.setOnClickListener {
            SubmitSettings(
                dpAILanguage.selectedItem,
                sbSpeed.progress
            )
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.dwLayout)
        val navView: NavigationView = findViewById(R.id.dwView)

        myToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)

        dwLayout.addDrawerListener(myToggle)

        myToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itmTTS -> {
                    ActivitySwaper(Intent(this, MainActivity::class.java))
                }

                R.id.itmPTTS -> {
                    ActivitySwaper(Intent(this, PictureTextToSpeech::class.java))
                }

                R.id.itmHelp -> {
                    ActivitySwaper(Intent(this, Help::class.java))
                }

                R.id.itmSettings -> {
                    ActivitySwaper(Intent(this, Settings::class.java))
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(myToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun ActivitySwaper(intent: Intent){
        startActivity(intent)
    }

    fun SubmitSettings(newLang: Any, newSpeed: Int){
        when(newLang){
                "English" ->{
                    TTSLang = Locale.ENGLISH
                }
                "German"->{
                    TTSLang = Locale.GERMAN
                }
                "Italian"->{
                    TTSLang = Locale.ITALIAN
                }
                "French"->{
                    TTSLang = Locale.FRENCH
                }
        }
        when(newSpeed){
            0 -> {
                TTSSpeed = 0.5f
                txtWPM.text = ".5x Spd"
            }
            1 -> {
                TTSSpeed = 1.0f
                txtWPM.text = "1x Spd"
            }
            2 -> {
                TTSSpeed = 2.0f
                txtWPM.text = "2x Spd"
            }
        }
    }
}