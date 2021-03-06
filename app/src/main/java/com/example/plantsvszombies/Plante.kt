package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect

abstract class Plante(val case : Case, var rayon : Float) {
    private var posX = case.posX
    private var posY = case.posY
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    var mort = false

    abstract var pv : Float
    abstract val sprite : Bitmap

    open fun draw(canvas : Canvas){canvas.drawBitmap(sprite, null, r.toRect(), null)}

    open fun set(){
        posX = case.posX
        posY = case.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }

    fun prendDegats(degats: Float){
        pv-=degats
        if(pv<=0){
            mort = true
            case.occupe = false
        }
    }

}