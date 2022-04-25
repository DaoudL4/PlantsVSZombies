package com.example.plantsvszombies

import android.content.res.Resources
import android.graphics.*
import androidx.core.graphics.toRect


class Tournesol(case: Case, var rayon : Float, soleil: Soleil) : Plante(case) {
    var posX = case.posX
    var posY = case.posY
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    var sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_sprite)

    init {
        soleil.changeFreq(1.2f)
    }

    override fun draw(canvas : Canvas){
        canvas.drawBitmap(sprite, null, r.toRect(), null)
    }

    fun set(){
        posX = case.posX
        posY = case.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }
}