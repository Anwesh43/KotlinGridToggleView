package com.example.anweshmishra.kotlingridicontoggleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.gridicontoggleview.GridIconToggleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GridIconToggleView.create(this)
    }
}
