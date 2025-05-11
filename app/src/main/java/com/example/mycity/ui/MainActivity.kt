package com.example.mycity.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycity.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // По умолчанию ActionBar скрыт (Theme без ActionBar)
        setContentView(R.layout.activity_main)
    }
}
