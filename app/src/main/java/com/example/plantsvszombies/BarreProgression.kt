package com.example.plantsvszombies

import android.graphics.*
import android.text.format.DateUtils

class BarreProgression(var x : Float, var y : Float, var longueur : Float, var largeur : Float) {
    private var paintFond = Paint()
    private var paintBarre = Paint()
    private var paintTime = Paint()
    private var paintTimeR = Paint()
    var progression = 0f
    var t = 0f

    private var rFond = RectF(x-longueur/2,y-largeur/2,x+longueur/2,y+largeur/2)
    private var rBarre = RectF(x-longueur/2,y-largeur/2,x+(progression*longueur),y+largeur/2)
    private var rTime = RectF(x-longueur/4,y-largeur,x+longueur/4,y+largeur)


    fun draw(canvas: Canvas){
        paintFond.color = Color.GRAY
        paintBarre.color = Color.GREEN

        canvas.drawRect(rFond, paintFond)
        canvas.drawRect(rBarre, paintBarre)
    }

    fun drawTime(canvas: Canvas){
        paintTimeR.color = Color.DKGRAY
        paintTime.color = Color.argb(255,255, 76, 48)
        paintTime.textSize = 100f
        paintTime.setTypeface(Typeface.DEFAULT_BOLD)
        paintTime.textAlign = Paint.Align.CENTER

        canvas.drawRect(rTime, paintTimeR)
        canvas.drawText(DateUtils.formatElapsedTime(t.toLong()), x, y - ((paintTime.descent() + paintTime.ascent()) / 2), paintTime)
    }

    fun set(){
        rFond.set(x-longueur/2,y-largeur/2,x+longueur/2,y+largeur/2)
        rBarre.set(x-longueur/2,y-largeur/2,x-longueur/2+(progression*longueur),y+largeur/2)
        rTime.set(x-longueur/4,y-largeur,x+longueur/4,y+largeur)
    }
}