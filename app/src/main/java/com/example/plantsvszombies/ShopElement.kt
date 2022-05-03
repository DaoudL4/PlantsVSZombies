package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect

class ShopElement(var shop : Shop, var x: Float, var y : Float, var plantelongueur : Float,
                  var plantelargeur : Float, var nom : String, var cout : Int,override var periodeTimer: Float,
                  var sprite : Bitmap, var sprite_inactif : Bitmap) : Timer{

    var r = RectF(x-plantelongueur/2,y-plantelargeur/2,x+plantelongueur/2,y+plantelargeur/2)
    var actif = false

    override var t0Timer = System.currentTimeMillis()

    fun draw(canvas : Canvas){
        if(actif) canvas.drawBitmap(sprite, null, r.toRect(), null)
        else canvas.drawBitmap(sprite_inactif, null, r.toRect(), null)
    }

    fun set(){
        r.set(x-plantelongueur/2,y-plantelargeur/2,x+plantelongueur/2,y+plantelargeur/2)
    }

    fun onTouch(xtouch : Float, ytouch : Float){
        if(r.contains(xtouch, ytouch) && shop.achetable(cout)){
            shop.achat(nom)
            resetTimer()
            actif = false
        }
    }

    override fun actionTimer() {
        actif = shop.achetable(cout)
    }
}