package com.example.plantsvszombies

import android.graphics.*
import androidx.core.graphics.toRect

class ShopElement(var shop : Shop, var x: Float, var y : Float, var plantelongueur : Float,
                  var plantelargeur : Float, var nom : String, var cout : Int,override var periodeTimer: Float,
                  var sprite : Bitmap, var sprite_inactif : Bitmap, var sprite_select : Bitmap) : Timer{

    var r = RectF(x-plantelongueur/2,y-plantelargeur/2,x+plantelongueur/2,y+plantelargeur/2)
    var actif = false
    var select = false

    var progression = 0f
    var rProgress = RectF(x-plantelongueur/2,y-plantelargeur/2,x+plantelongueur/2,y-(progression*plantelargeur)+plantelargeur/2)
    var paintProgress = Paint()

    override var t0Timer = System.currentTimeMillis()

    init {
        paintProgress.color = Color.argb(150, 0, 0 , 0)
    }

    fun draw(canvas : Canvas){
        if(actif) {
            if(!select) canvas.drawBitmap(sprite, null, r.toRect(), null)
            else canvas.drawBitmap(sprite_select, null, r.toRect(), null)
        }
        else {
            canvas.drawBitmap(sprite_inactif, null, r.toRect(), null)
            canvas.drawRect(rProgress, paintProgress)
        }
    }

    fun set(){
        r.set(x-plantelongueur/2,y-plantelargeur/2,x+plantelongueur/2,y+plantelargeur/2)
        rProgress.set(x-plantelongueur/2,y-plantelargeur/2,x+plantelongueur/2,y-(progression*plantelargeur)+plantelargeur/2)
    }

    fun onTouch(xtouch : Float, ytouch : Float){
        if(r.contains(xtouch, ytouch) && actif){
            shop.achat(this)
            shop.resetSelect()
            select = true
        }
    }

    override fun actionTimer() {
        actif = shop.achetable(cout)
    }

    fun setProgression() {
        if(progression < 1) progression = ((System.currentTimeMillis()-t0Timer)/(1000*periodeTimer))
    }

    override fun resetTimer() {
        super.resetTimer()
        progression = 0f
    }
}