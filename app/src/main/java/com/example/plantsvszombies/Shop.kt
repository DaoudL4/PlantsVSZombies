package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent

class Shop (var x1 : Float, var y1: Float, var x2: Float, var y2: Float) {
    var shopPaint = Paint()
    val r = RectF(x1, y1, x2, y2)

    var tPaint = Paint()
    var tx = 0f
    var ty = 0f
    var trad = 0f
    val tr = RectF(tx-trad,ty-trad,tx+trad,ty+trad)

    lateinit var plante_touchee : String

    fun onTouch(e : MotionEvent){

    }


    fun draw(canvas : Canvas){
        shopPaint.color = Color.BLUE
        tPaint.color = Color.YELLOW
        canvas.drawRect(r, shopPaint)
        canvas.drawOval(tr, tPaint)
    }

    fun set(){
        r.set(x1, y1, x2, y2)
        tr.set(tx-trad,ty-trad,tx+trad,ty+trad)
    }

    fun achetable(){

    }
}