package com.example.plantsvszombies

import android.graphics.BitmapFactory
import com.example.plantsvszombies.App
import com.example.plantsvszombies.Case
import com.example.plantsvszombies.Plante
import com.example.plantsvszombies.R

class ZombieMain(case: Case, rayon: Float) : Plante(case, rayon) {
    override val sprite = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.zombiemain
    )
    override var pv = 1f
}