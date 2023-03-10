package com.example.m08finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.m08finalproject.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class Help : AppCompatActivity() {
    lateinit var myToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
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