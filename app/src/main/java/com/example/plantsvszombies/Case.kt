package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Case(var posX : Float, var posY : Float) {
    var paint = Paint()
    var occupe = false
    val largeur = 50

    val r = RectF((posX-largeur/2), (posY-largeur/2),(posX+largeur/2),(posY+largeur/2))

    fun draw(canvas: Canvas) {
        paint.color = Color.GREEN
        canvas.drawRect(r, paint)
    }

}