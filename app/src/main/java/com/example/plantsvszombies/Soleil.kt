package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent

class Soleil(var credit: Credit, var x : Float, var y: Float, var rayon: Float) {
    var periode = 3
    var t0 = 0L
    var etat = true
    val increment = 50

    var paint = Paint()
    val r = RectF(x-rayon, y-rayon,x+rayon, y+rayon)

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(r.contains(xtouch, ytouch) && etat){
            incrementer()
            t0 = System.currentTimeMillis()
            changeEtat()
        }
    }

    fun changeEtat(){
        etat = !etat
    }

    fun incrementer(){
        credit.updateCredit(increment)
    }

    fun draw(canvas: Canvas) {
        if(etat) paint.color = Color.argb(255,255,165,0)
        else paint.color = Color.argb(100,255,165,0)
        canvas.drawOval(r, paint)
    }

    fun set(){
        r.set(x-rayon, y-rayon,x+rayon, y+rayon)
    }

}