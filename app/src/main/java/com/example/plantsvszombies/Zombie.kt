package com.example.plantsvszombies

import android.graphics.*
import android.util.Log
import androidx.core.graphics.toRect


class Zombie(var ligne: Int, var rayon : Float,var listeCase : Array<Array<Case>>, val view: GameView) {
    var case = listeCase[ligne][8]
    var posX = case.posX
    var posY = case.posY
    var estGele = false
    var vitesse =0.03
    var pv = 200
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    var avance = true

    var sprite_normal = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie)
    var sprite_gele = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie_gel)



    fun draw(canvas : Canvas){
        if(!estGele) canvas.drawBitmap(sprite_normal, null, r.toRect(), null)
        else canvas.drawBitmap(sprite_gele, null, r.toRect(), null)
    }

    fun avance(interval : Double){
        currentCase()

        if(avance) {
            if(!estGele){
                r.offset(-(vitesse * interval).toFloat(), 0f)
            }
            else{
                r.offset(-(vitesse/5 * interval).toFloat(), 0f)
            }

        }
        /*
        if(depasse()){
            view.gameOver_lose()
        }

         */
    }

    fun set(){
        posX = case.posX
        posY = case.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }

    fun currentCase(){
        for(i in listeCase[ligne]){
            if(i.case.contains(r.centerX(), r.centerY())){
                if (i.occupe){
                    avance = false
                }
            }
        }
    }

    fun depasse() : Boolean{
        return (r.centerX()<listeCase[ligne][0].posX)
    }

    fun gele() {
        estGele = true
    }
}
