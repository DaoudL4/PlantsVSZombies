package com.example.plantsvszombies

import android.graphics.*
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Soleil(var credit: Credit, var x : Float, var y: Float, var rayon: Float){
    var periode = 0f
    var t0 = 0L
    var etat = true
    val increment = 50

    var paintFond = Paint()
    val r = RectF(x-rayon, y-rayon,x+rayon, y+rayon)
    var spriteActif = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sun_sprite)
    var spriteInactif = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sun_sprite_desac)


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
        paintFond.color = Color.argb(255,3, 138, 255)
        canvas.drawOval(r, paintFond)
        if(etat) canvas.drawBitmap(spriteActif, null, r.toRect(), null)
        else canvas.drawBitmap(spriteInactif, null, r.toRect(), null)
    }

    fun changeFreq(coefficient : Float){
        periode/=coefficient
    }

    fun set(){
        r.set(x-rayon, y-rayon,x+rayon, y+rayon)
    }

    fun reset(){
        t0 = System.currentTimeMillis()
        periode = 5f
    }

}