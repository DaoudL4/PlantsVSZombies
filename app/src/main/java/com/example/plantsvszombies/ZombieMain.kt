package com.example.plantsvszombies

import android.graphics.BitmapFactory

class ZombieMain(case: Case, rayon: Float) : Plante(case, rayon) {
    override val sprite = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.zombiemain
    )
    override var pv = 1f
}