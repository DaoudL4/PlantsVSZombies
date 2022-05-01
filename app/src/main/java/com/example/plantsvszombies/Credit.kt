package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Credit(var x : Float, var y: Float, var rayon: Float) {
    var paint = Paint()
    var textpaint = Paint()
    var police = 0
    val r = RectF(x-rayon, y-rayon,x+rayon, y+rayon)

    var credit = 50

    fun draw(canvas : Canvas){
        paint.color = Color.GREEN
        textpaint.color = Color.WHITE
        textpaint.textSize = 50f
        textpaint.textAlign = Paint.Align.CENTER
        canvas.drawOval(r, paint)
        canvas.drawText(credit.toString(), x, y, textpaint)
    }

    fun set(){
        r.set(x-rayon, y-rayon,x+rayon, y+rayon)
    }

    fun updateCredit(montant : Int) {
        credit += montant
    }

    fun reset(){
        credit = 0
    }
}