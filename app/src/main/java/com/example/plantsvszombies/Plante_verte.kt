package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Plante_verte(case: Case, rayon : Float, zombie: Zombie) : Plante_attaque(case, rayon, zombie) {
    override val sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sprite_verte)

}