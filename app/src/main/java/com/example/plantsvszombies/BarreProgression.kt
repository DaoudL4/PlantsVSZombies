package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class BarreProgression(var x : Float, var y : Float, var longueur : Float, var largeur : Float) {
    var paintFond = Paint()
    var paintBarre = Paint()
    var progression = 0f

    var rFond = RectF(x-longueur/2,y-largeur/2,x+longueur/2,y+largeur/2)
    var rBarre = RectF(x-longueur/2,y-largeur/2,x+(progression*longueur),y+largeur/2)


    fun draw(canvas: Canvas){
        paintFond.color = Color.GRAY
        paintBarre.color = Color.GREEN

        canvas.drawRect(rFond, paintFond)
        canvas.drawRect(rBarre, paintBarre)
    }

    fun set(){
        rFond.set(x-longueur/2,y-largeur/2,x+longueur/2,y+largeur/2)
        rBarre.set(x-longueur/2,y-largeur/2,x-longueur/2+(progression*longueur),y+largeur/2)
    }
}