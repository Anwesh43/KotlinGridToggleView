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

    data class State (var scale : Float = 0f, var deg : Double = 0.0, var dir : Float = 0f, var prevDeg : Double = 0.0) {

        fun update(stopcb : (Float) -> Unit) {
            deg += (Math.PI/16) * dir
            scale = Math.sin(deg).toFloat()
            if (Math.abs(deg - prevDeg) > Math.PI/2) {
                deg = prevDeg + (Math.PI/2) * dir
                dir = 0f
                scale = Math.sin(deg).toFloat()
                prevDeg = deg
                stopcb(scale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * scale
                startcb()
            }
        }
    }
}