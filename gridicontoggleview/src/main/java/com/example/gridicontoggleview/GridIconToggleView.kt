package com.example.gridicontoggleview

/**
 * Created by anweshmishra on 16/05/18.
 */

import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.graphics.*
import android.util.Log

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

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class GridIconToggle(var i : Int, val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val r : Int = 0
            val g : Int = 137
            val b : Int = 123
            paint.strokeWidth = Math.min(w, h)/50
            paint.strokeCap = Paint.Cap.ROUND
            val wGap : Float = Math.min(w, h) * 0.35f
            val updateColor : ((Int, Float) -> Int) = {cf, scale ->
                Log.d("colorFactor:",cf.toString())
                255 + ((cf - 255) * scale).toInt()
            }
            val hGap : Float = 2 * h/5 * state.scale
            val radius : Float = Math.min(w,h)/20
            canvas.save()
            canvas.translate(w/2, h/2)
            for (i in 0..2) {
                canvas.save()
                canvas.translate(0f, (1 - i) * hGap)
                for (j in 0..2) {
                    canvas.save()
                    canvas.translate((1-j) * wGap, 0f)
                    paint.color = Color.rgb(updateColor(r, state.scale), updateColor(g, state.scale), updateColor(b, state.scale))
                    canvas.drawCircle(0f, 0f, radius, paint)
                    paint.color = Color.rgb(updateColor(r, 1 - state.scale), updateColor(g, 1 - state.scale), updateColor(b, 1 - state.scale))
                    for (r in 0..1) {
                        canvas.save()
                        canvas.rotate(90f * i * (1 - state.scale))
                        canvas.drawLine(0f, -radius/2, 0f, radius/2, paint)
                        canvas.restore()
                    }
                    canvas.restore()
                }
                canvas.restore()
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : GridIconToggleView) {

        val gridIconToggle : GridIconToggle = GridIconToggle(0)

        val animator : Animator = Animator(view)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            gridIconToggle.draw(canvas, paint)
            animator.animate {
                gridIconToggle.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            gridIconToggle.startUpdating {
                animator.start()
            }
        }
    }
}