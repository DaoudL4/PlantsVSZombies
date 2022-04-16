package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Case(var posX : Float, var posY : Float, var largeur : Float, var longueur : Float) {
    var paint = Paint()
    var occupe = false
    var pair = 0

    val case = RectF((posX-largeur/2), (posY-longueur/2),(posX+largeur/2),(posY+longueur/2))

    fun draw(canvas: Canvas) {
        if(pair==0) paint.color = Color.GREEN
        else paint.color = Color.argb(255,30,130,76)

        canvas.drawRect(case, paint)
    }

    fun setcase(){
        case.set((posX-largeur/2), (posY-longueur/2),(posX+largeur/2),(posY+longueur/2))
    }

}