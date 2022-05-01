package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.concurrent.ConcurrentLinkedQueue

class Plante_verte(case: Case, rayon : Float, zombies: ArrayList<Zombie>, plantes : ConcurrentLinkedQueue<Plante>) : Plante_attaque(case, rayon, zombies, plantes) {
    override val sprite = BitmapFactory.decodeResource(App.instance.resources,
        R.drawable.sprite_verte
    )

}