package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF


class Zombie(var case: Case, var rayon : Float, posX : Float, posY : Float ) {
    var posX = case.posX
    var posY = case.posY
    var vitesse = 200
    var pv = 200
    var paint = Paint()
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    fun draw(canvas : Canvas){
        paint.color = Color.GRAY
        canvas.drawOval(r, paint)
    }
    fun set(){
        posX = case.posX
        posY = case.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }
}
