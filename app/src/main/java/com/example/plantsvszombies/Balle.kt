package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect
import java.util.concurrent.ConcurrentLinkedQueue

open class Balle(val indice : Int, val planteAttaque: Plante_attaque, val zombies: ArrayList<Zombie>, val plantes: ConcurrentLinkedQueue<Plante>) {
    val case0 = planteAttaque.case
    var posX = case0.posX
    var posY = case0.posY
    var rayon = 50f
    open var sprite =  BitmapFactory.decodeResource(App.instance.resources, R.drawable.balle)
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    lateinit var zombietouche: Zombie
    val vitesse = 1
    val degats = 1
    var disparait = false

    open fun launch(interval : Double) {
        r.offset((vitesse * interval).toFloat(), 0f)

        if(toucheZombie()){
            quandToucheZombie()
        }
        if(toucheBuche()){
            enflamme()
        }
    }

    open fun enflamme() {
        sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.balle_feu)
    }

    fun toucheBuche(): Boolean {
        var res = false
        for (p in plantes){
            if(p is Buche && p.r.contains(r.centerX(), r.centerY())){
                res = true
            }
        }
        return res
    }

    open fun quandToucheZombie() {
        zombietouche.prendDegats(degats)
        disparait = true
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