package com.dicoding.wecare

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.schedule

class Spalshscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        Timer("Splash Screen").schedule(3000){
           val intent = Intent(this@Spalshscreen, MainActivity::class.java)
           startActivity(intent)
           finish()
        }
    }
}