package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Plante_glace(case: Case, rayon : Float, zombies: ArrayList<Zombie>) : Plante_attaque(case, rayon, zombies) {
    override val sprite = BitmapFactory.decodeResource(App.instance.resources,
        R.drawable.plante_glace_sprite
    )

    override fun tir() {
        balles.add(Balle_glace(balles.size-1, this, zombies))
        t0 = System.currentTimeMillis()
    }
}