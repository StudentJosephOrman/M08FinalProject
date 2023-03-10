package com.example.m08finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnSpeak
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object{
        var TTSLang = Locale.ENGLISH
        var TTSSpeed = 1.0f
    }
    lateinit var myToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSpeak.setOnClickListener{
            TTS(this@MainActivity, multiTxtSpeechText.text.toString(), TTSLang, TTSSpeed)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.dwLayout)
        val navView: NavigationView = findViewById(R.id.dwView)

        myToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)

        dwLayout.addDrawerListener(myToggle)

        myToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener{
            when(it.itemId){
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
}