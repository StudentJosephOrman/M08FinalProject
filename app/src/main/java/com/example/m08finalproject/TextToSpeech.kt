package com.example.m08finalproject

import android.app.Activity
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.*

class TTS(private val section: Activity, private val message: String, val lang: Locale, val aiRate: Float): TextToSpeech.OnInitListener {
    private val speaker = TextToSpeech(section, this)

    override fun onInit(x: Int) {
        if (x == TextToSpeech.SUCCESS) {
            val result: Int = speaker.setLanguage(lang)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(section, "Language Not Supported or Not Found", Toast.LENGTH_SHORT).show()
            } else {
                translate(message)
            }
        } else {
            Toast.makeText(section, "Failed to initialize TTS", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speech(message: String) {
        speaker.setSpeechRate(aiRate)
        speaker.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun translate(message: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(Locale.ENGLISH.toLanguageTag())
            .setTargetLanguage(lang.toLanguageTag())
            .build()

        val translator = Translation.getClient(options)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        var transMessage = ""

        translator.downloadModelIfNeeded(conditions)
            .addOnCompleteListener {task ->
                Log.i("$task", "Downloading Finished task(string): ${task.toString()}")
            }.addOnSuccessListener {
                Log.i("Download Success", "Model successfully installed!")
            }
            .addOnFailureListener { exception ->
                Log.i("Download Fail", "Model download failed, try again!")
            }

        translator.translate(message)
            .addOnCompleteListener {task ->
                Log.i("$task", "Translating Finished task(string): ${task.toString()}")
            }.addOnSuccessListener { translatedText ->
                translator.close()
                Log.i("Translation Successful", "Message successfully translated: ${translatedText}")
                speech(translatedText)
            }
            .addOnFailureListener { exception ->
                Log.i("Translation failed", "Error: ${exception}")
            }
    }
}

