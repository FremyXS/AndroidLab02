package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var bind : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bind.root)
    }
}