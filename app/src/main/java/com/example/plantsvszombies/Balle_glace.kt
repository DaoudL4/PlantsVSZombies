package com.example.plantsvszombies

import android.graphics.BitmapFactory
import java.util.concurrent.ConcurrentLinkedQueue

class Balle_glace(indice: Int, planteAttaque: Plante_attaque, view : GameView) : Balle(indice, planteAttaque, view) {
    override var sprite =  BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.balle_glace
    )
    private var gele = true

    override fun quandToucheZombie() {
        super.quandToucheZombie()
        if(gele) zombietouche.gele()
    }

    override fun enflamme() {
        sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.balle)
        gele = false
    }

}