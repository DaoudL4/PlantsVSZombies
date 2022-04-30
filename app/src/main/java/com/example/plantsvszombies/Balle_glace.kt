package com.example.plantsvszombies

import android.graphics.BitmapFactory

class Balle_glace(indice: Int, planteAttaque: Plante_attaque, zombies: ArrayList<Zombie>) : Balle(indice, planteAttaque, zombies) {
    override val sprite =  BitmapFactory.decodeResource(App.instance.resources,
        R.drawable.balle_glace
    )

    override fun quandToucheZombie() {
        super.quandToucheZombie()
        zombietouche.gele()
    }

}