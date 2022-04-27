package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect

class Balle(val indice : Int, val planteAttaque: Plante_attaque, val zombie: Zombie) {
    val case0 = planteAttaque.case
    var posX = case0.posX
    var posY = case0.posY
    var rayon = 50f
    val sprite =  BitmapFactory.decodeResource(App.instance.resources, R.drawable.balle)
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)

    val vitesse = 1
    val degats = 10

    fun launch(interval : Double) {
        r.offset((vitesse * interval).toFloat(), 0f)

        if(doitDisparaitre()){
            destruction()
        }
    }

    fun doitDisparaitre() : Boolean{
        return(zombie.r.contains(r.centerX(), r.centerY()))
    }

    fun destruction(){
        planteAttaque.delBalle(indice)
    }

    fun draw(canvas : Canvas){canvas.drawBitmap(sprite, null, r.toRect(), null)}

    fun set(){
        posX = case0.posX
        posY = case0.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }
}