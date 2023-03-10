package com.example.m08finalproject

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import androidx.drawerlayout.widget.DrawerLayout
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_picture_text_to_speech.*
import com.example.m08finalproject.MainActivity.Companion
import com.example.m08finalproject.MainActivity.Companion.TTSLang
import com.example.m08finalproject.MainActivity.Companion.TTSSpeed
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_picture_text_to_speech.*
import kotlinx.android.synthetic.main.activity_picture_text_to_speech.dwLayout
import kotlinx.android.synthetic.main.activity_settings.*

class PictureTextToSpeech : AppCompatActivity() {
    lateinit var myToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_text_to_speech)
        btnTakePhoto.isEnabled = false
        btnPhotoSpeak.setOnClickListener{PhotoTextToText()}
        btnTakePhoto.setOnClickListener{TakePhoto()}

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 111)
        }else{
            btnTakePhoto.isEnabled = true
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            btnTakePhoto.isEnabled = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==101){
            imgCamera.setImageBitmap(data?.getParcelableExtra<Bitmap>("data"))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(myToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun TakePhoto(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 101)
    }

    fun PhotoTextToText() {
        var textReader = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        try {
            val inputImg = InputImage.fromBitmap(imgCamera.drawToBitmap(),0)
            val txtTaskResult = textReader.process(inputImg)
                .addOnSuccessListener{text ->
                    txtPictureToText.text = text.text
                    TTS(this@PictureTextToSpeech, txtPictureToText.text.toString(), TTSLang, TTSSpeed)
                }
                .addOnFailureListener{ e ->
                    Toast.makeText(this, "Failed to recognize text, due to ${e.message}",Toast.LENGTH_LONG).show()
                }
        }catch (e: Exception){
            Toast.makeText(this, "Failed to prepare image, due to ${e.message}",Toast.LENGTH_LONG).show()
        }
    }

    fun ActivitySwaper(intent: Intent){
        startActivity(intent)
    }
}