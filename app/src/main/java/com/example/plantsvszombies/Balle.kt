package com.example.plantsvszombies

import android.app.GameManager
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect
import java.util.concurrent.ConcurrentLinkedQueue

open class Balle(val indice : Int, private val planteAttaque: Plante_attaque, view : GameView) {
    private val case0 = planteAttaque.case
    private var posX = case0.posX
    private var posY = case0.posY
    private var rayon = 50f
    open var sprite =  BitmapFactory.decodeResource(App.instance.resources, R.drawable.balle)
    private val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)

    private var plantes = view.plantes
    private var zombies = view.zombies
    lateinit var zombietouche: Zombie
    private val vitesse = 1
    private var degats = 1
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
        degats = 2
    }

    private fun toucheBuche(): Boolean {
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

    private fun toucheZombie() : Boolean{
        var res = false
        for (z in zombies){
            if(r.contains(z.r.centerX(), z.r.centerY())){
                res = true
                zombietouche = z
            }
        }
        return res
    }

    private fun destruction(){
        planteAttaque.delBalle(indice)
    }

    fun draw(canvas : Canvas){canvas.drawBitmap(sprite, null, r.toRect(), null)}

    fun set(){
        posX = case0.posX
        posY = case0.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }
}