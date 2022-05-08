package com.example.plantsvszombies

import android.graphics.BitmapFactory
import kotlin.random.Random

class Zombie_mage(ligne: Int, rayon : Float, listeCase : Array<Array<Case>>, view: GameView) : Zombie(ligne, rayon, listeCase, view), Timer {
    override var pv = 5
    override val degats = 0
    override var sprite_normal = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie_mage)
    override var sprite_gele = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie_mage_gele)

    override var t0Timer = System.currentTimeMillis()
    override var periodeTimer = 5f

    var listeCaseOccupe = ArrayList<Case>()


    fun disparaitPlante(){
        for (i in 0..view.ncaseY-1){
            for (j in 0..view.ncaseX-1){
                if (listeCase[i][j].occupe) listeCaseOccupe.add(listeCase[i][j])
            }
        }
        if(listeCaseOccupe.isNotEmpty()){
            val case = listeCaseOccupe[Random.nextInt(0,listeCaseOccupe.size)]
            view.detruitPlante(case)
            view.spawnMain(case)
        }
    }

    override fun actionTimer() {
        disparaitPlante()
        resetTimer()
    }

}