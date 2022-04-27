package com.example.plantsvszombies

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Plante_verte(case: Case, rayon : Float) : Plante_attaque(case, rayon) {
    override val sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sprite_verte)

}