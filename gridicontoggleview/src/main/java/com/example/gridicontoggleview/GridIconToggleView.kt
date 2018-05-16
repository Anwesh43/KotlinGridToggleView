package com.example.gridicontoggleview

/**
 * Created by anweshmishra on 16/05/18.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.graphics.*

class GridIconToggleView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    override fun onDraw(canvas : Canvas) {

    }
}