package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect

abstract class Plante_attaque(case: Case, rayon : Float, val zombie: Zombie) : Plante(case, rayon) {
    val periode_tir = 2
    var t0 = 0L
    open var balles = ArrayList<Balle>()

    fun tir(){
        balles.add(Balle(balles.size-1, this, zombie))
        t0 = System.currentTimeMillis()
    }

    fun avanceBalles(interval: Double){
        for (i in balles) {
            i.launch(interval)
        }
    }

    fun delBalle(indice: Int) {
        balles.removeFirst()
    }

    override fun draw(canvas: Canvas){
        super.draw(canvas)
        for(i in balles){
            i.draw(canvas)
        }
    }
    override fun set(){
        super.set()
        for(i in balles){
            i.set()
        }
    }

}