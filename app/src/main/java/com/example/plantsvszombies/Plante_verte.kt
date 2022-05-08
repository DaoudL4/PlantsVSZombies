package com.example.plantsvszombies

import android.graphics.BitmapFactory
import java.util.concurrent.ConcurrentLinkedQueue

class Plante_verte(case: Case, rayon : Float, view : GameView) : Plante_attaque(case, rayon, view) {
    override val sprite = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.sprite_verte
    )
    override var pv = 3000f

}