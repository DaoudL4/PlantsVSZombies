package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect

open class Balle(val indice : Int, val planteAttaque: Plante_attaque, val zombies: ArrayList<Zombie>) {
    val case0 = planteAttaque.case
    var posX = case0.posX
    var posY = case0.posY
    var rayon = 50f
    open val sprite =  BitmapFactory.decodeResource(App.instance.resources, R.drawable.balle)
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    lateinit var zombietouche: Zombie
    val vitesse = 1
    val degats = 10

    open fun launch(interval : Double) {
        r.offset((vitesse * interval).toFloat(), 0f)

        if(toucheZombie()){
            quandToucheZombie()
        }
    }

    open fun quandToucheZombie() {
        destruction()
    }

    fun toucheZombie() : Boolean{
        var res = false
        for (z in zombies){
            if(z.r.contains(r.centerX(), r.centerY())){
                res = true
                zombietouche = z
            }
        }
        return res
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