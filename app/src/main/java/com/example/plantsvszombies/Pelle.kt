package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Pelle(var x : Float, var y: Float, var rayon: Float) {
    private val r = RectF(x-rayon, y-rayon,x+rayon, y+rayon)
    private var sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.pelle)
    private val sprite_click = BitmapFactory.decodeResource(App.instance.resources, R.drawable.pelle_clicked)
    var destruction = false

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(r.contains(xtouch, ytouch)){
            destruction = true
        }
    }

    fun draw(canvas: Canvas){
        if(!destruction) canvas.drawBitmap(sprite, null, r.toRect(), null)
        else canvas.drawBitmap(sprite_click, null, r.toRect(), null)
    }
    fun set(){
        r.set(x-rayon, y-rayon,x+rayon, y+rayon)
    }

    fun reset() {
        destruction = false
    }
}