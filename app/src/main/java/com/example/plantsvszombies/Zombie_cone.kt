package com.example.plantsvszombies

import android.graphics.BitmapFactory

class Zombie_cone(ligne: Int, rayon : Float, listeCase : Array<Array<Case>>, view: GameView) : Zombie(ligne, rayon, listeCase, view) {
    override var pv = 10
    override var sprite_normal = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie_cone_sprite)
    override var sprite_gele = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie_cone_gele_sprite)

}