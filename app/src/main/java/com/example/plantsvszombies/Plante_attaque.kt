package com.example.plantsvszombies

import android.graphics.Canvas
import java.util.concurrent.ConcurrentLinkedQueue

abstract class Plante_attaque(case: Case, rayon : Float, val view: GameView) : Plante(case, rayon), Timer {
    override var periodeTimer = 2f
    override var t0Timer = 0L
    var balles = ConcurrentLinkedQueue<Balle>()

    open fun tir(){
        balles.add(Balle(balles.size-1, this, view))
        resetTimer()
    }

    override fun actionTimer() {
        tir()
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