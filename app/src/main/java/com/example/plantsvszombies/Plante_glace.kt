package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.concurrent.ConcurrentLinkedQueue

class Plante_glace(case: Case, rayon : Float, zombies: ArrayList<Zombie>, plantes: ConcurrentLinkedQueue<Plante>) : Plante_attaque(case, rayon, zombies, plantes) {
    override val sprite = BitmapFactory.decodeResource(App.instance.resources,
        R.drawable.plante_glace_sprite
    )
    override var pv = 3000f

    override fun tir() {
        balles.add(Balle_glace(balles.size-1, this, zombies, plantes))
        t0 = System.currentTimeMillis()
    }
}