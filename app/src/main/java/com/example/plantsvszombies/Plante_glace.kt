package com.example.plantsvszombies

import android.graphics.BitmapFactory
import java.util.concurrent.ConcurrentLinkedQueue

class Plante_glace(case: Case, rayon : Float, view : GameView) : Plante_attaque(case, rayon, view) {
    override val sprite = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.plante_glace_sprite
    )
    override var pv = 3000f

    override fun tir() {
        balles.add(Balle_glace(balles.size-1, this, view))
        resetTimer()
    }
}