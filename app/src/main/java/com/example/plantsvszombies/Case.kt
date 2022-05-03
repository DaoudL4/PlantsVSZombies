package com.example.plantsvszombies

import android.graphics.*
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Case(var posX : Float, var posY : Float, var largeur : Float, var longueur : Float, var view : GameView) {
    val sprite1 = BitmapFactory.decodeResource(App.instance.resources, R.drawable.herbe1)
    val sprite2 = BitmapFactory.decodeResource(App.instance.resources, R.drawable.herbe2)

    var occupe = false
    var pair = 0
    lateinit var plante : Plante

    val case = RectF((posX-largeur/2), (posY-longueur/2),(posX+largeur/2),(posY+longueur/2))

    fun draw(canvas: Canvas) {
        if(pair==0) canvas.drawBitmap(sprite1, null, case.toRect(), null)
        else canvas.drawBitmap(sprite2, null, case.toRect(), null)

    }

    fun onTouch(e : MotionEvent, achat : Boolean, pelle : Boolean){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(case.contains(xtouch, ytouch)){
            if(!occupe && achat){
                occupe = true
                view.achatPlante( this)
            }
            else if (occupe && pelle) {
                occupe = false
                view.detruitPlante(this)
            }

        }
    }

    fun setcase(){
        case.set((posX-largeur/2), (posY-longueur/2),(posX+largeur/2),(posY+longueur/2))
    }

    fun reset(){
        occupe = false
    }

}