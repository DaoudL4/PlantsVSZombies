package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Pelle(var x : Float, var y: Float, var rayon: Float) {
    val r = RectF(x-rayon, y-rayon,x+rayon, y+rayon)
    var sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.pelle)
    var destruction = false

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(r.contains(xtouch, ytouch)){
            destruction = true
        }
    }

    fun draw(canvas: Canvas){
        canvas.drawBitmap(sprite, null, r.toRect(), null)
    }
    fun set(){
        r.set(x-rayon, y-rayon,x+rayon, y+rayon)
    }
}