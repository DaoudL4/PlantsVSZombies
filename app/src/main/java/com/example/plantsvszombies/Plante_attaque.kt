package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.toRect
import java.util.concurrent.ConcurrentLinkedQueue

abstract class Plante_attaque(case: Case, rayon : Float, val zombies: ArrayList<Zombie>, val plantes: ConcurrentLinkedQueue<Plante>) : Plante(case, rayon) {
    val periode_tir = 1
    var t0 = 0L
    var balles = ConcurrentLinkedQueue<Balle>()

    open fun tir(){
        balles.add(Balle(balles.size-1, this, zombies, plantes))
        t0 = System.currentTimeMillis()
    }

    fun avanceBalles(interval: Double){
        val iterator = balles.iterator()
        while(iterator.hasNext()){
            val b = iterator.next()
            b.launch(interval)
        }

    }

    fun delBalle(indice : Int) {
        balles.removeAll{it.indice == indice && it.disparait == true}
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