package com.example.plantsvszombies

import android.graphics.BitmapFactory

class Buche(case: Case, rayon: Float) : Plante(case, rayon) {
    override val sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche)
}