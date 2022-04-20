package com.example.plantsvszombies

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF



class Tournesol(case: Case, var rayon : Float) : Plante(case) {
    var posX = case.posX
    var posY = case.posY
    var paint = Paint()
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)


    override val cout = 50

    override fun draw(canvas : Canvas){
        paint.color = Color.YELLOW
        canvas.drawOval(r, paint)
    }

    fun set(){
        posX = case.posX
        posY = case.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }
}