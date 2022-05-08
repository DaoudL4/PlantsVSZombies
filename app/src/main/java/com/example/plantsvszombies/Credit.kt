package com.example.plantsvszombies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Credit(var x : Float, var y: Float, var rayon: Float) {
    private var paint = Paint()
    private var textpaint = Paint()
    private val r = RectF(x-rayon, y-rayon/2,x+rayon, y+rayon/2)

    var credit = 50

    fun draw(canvas : Canvas){
        paint.color = Color.argb(150, 0, 0 , 0)
        textpaint.color = Color.WHITE
        textpaint.textSize = 50f
        textpaint.textAlign = Paint.Align.CENTER
        canvas.drawRect(r, paint)
        canvas.drawText(credit.toString(), x, y - ((textpaint.descent() + textpaint.ascent()) / 2), textpaint)
    }

    fun set(){
        r.set(x-rayon, y-rayon/2,x+rayon, y+rayon/2)
    }

    fun updateCredit(montant : Int) {
        credit += montant
    }

    fun reset(){
        credit = 50
    }
}