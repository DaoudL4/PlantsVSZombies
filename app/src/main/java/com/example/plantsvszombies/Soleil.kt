package com.example.plantsvszombies

import android.graphics.*
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Soleil(private var credit: Credit, var x : Float, var y: Float, var rayon: Float) : Timer{
    override var periodeTimer = 5f
    override var t0Timer = 0L

    private var etat = true
    private val increment = 50

    private var paintFond = Paint()
    private val r = RectF(x-rayon, y-rayon,x+rayon, y+rayon)
    private var spriteActif = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sun_sprite)
    private var spriteInactif = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sun_sprite_desac)


    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(r.contains(xtouch, ytouch) && etat){
            incrementer()
            resetTimer()
            changeEtat()
        }
    }

    override fun actionTimer() {
        if(!etat){
            changeEtat()
        }
    }

    private fun changeEtat(){
        etat = !etat
    }

    private fun incrementer(){
        credit.updateCredit(increment)
    }

    fun draw(canvas: Canvas) {
        paintFond.color = Color.argb(255,3, 138, 255)
        canvas.drawOval(r, paintFond)
        if(etat) canvas.drawBitmap(spriteActif, null, r.toRect(), null)
        else canvas.drawBitmap(spriteInactif, null, r.toRect(), null)
    }

    fun changeFreq(coefficient : Float){
        periodeTimer/=coefficient
    }

    fun set(){
        r.set(x-rayon, y-rayon,x+rayon, y+rayon)
    }

    fun reset(){
        t0Timer = System.currentTimeMillis()
        periodeTimer = 5f
    }

}