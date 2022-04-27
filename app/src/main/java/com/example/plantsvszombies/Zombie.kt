package com.example.plantsvszombies

import android.graphics.*
import android.util.Log
import androidx.core.graphics.toRect


class Zombie(var ligne: Int, var rayon : Float,var listeCase : Array<Array<Case>>, val view: GameView) {
    var case = listeCase[ligne][8]
    var posX = case.posX
    var posY = case.posY
    var vitesse =0.03
    var pv = 200
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    var avance = true

    var sprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie)



    fun draw(canvas : Canvas){
        canvas.drawBitmap(sprite, null, r.toRect(), null)
    }

    fun avance(interval : Double){
        currentCase()

        if(avance) {
            r.offset(-(vitesse * interval).toFloat(), 0f)
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
}
