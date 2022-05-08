package com.example.plantsvszombies

import android.graphics.*
import androidx.core.graphics.toRect


open class Zombie(private var ligne: Int, var rayon : Float, var listeCase : Array<Array<Case>>, val view: GameView) {
    var case = listeCase[ligne][8]
    private var posX = case.posX
    private var posY = case.posY
    private var estGele = false
    private var vitesse = 0.03
    open var pv = 8
    val r = RectF(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    private var avance = true
    private lateinit var caseAttaque : Case
    open val degats = 1
    var mort = false

    open var sprite_normal = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie)
    open var sprite_gele = BitmapFactory.decodeResource(App.instance.resources, R.drawable.zombie_gel)

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
        else{
            attaque((degats*interval).toFloat())
        }

        if(depasse()){
            view.gameOver_lose()
        }


    }

    private fun attaque(degats : Float) {
        try {
            caseAttaque.plante.prendDegats(degats)
        }catch (e : UninitializedPropertyAccessException){}finally {

        }

    }

    fun set(){
        posX = case.posX
        posY = case.posY
        r.set(posX-rayon, posY-rayon,posX+rayon, posY+rayon)
    }

    private fun currentCase(){
        for(i in listeCase[ligne]){
            if(i.case.contains(r.centerX(), r.centerY())){
                if (i.occupe ){
                    try{
                        if(i.plante !is ZombieMain){
                            caseAttaque = i
                            avance = false
                        }
                    }catch (e : UninitializedPropertyAccessException){
                        caseAttaque = i
                        avance = false

                        println("exception levee")
                    }
                }
                else{
                    avance = true
                }
            }
        }
    }

    private fun depasse() : Boolean{
        return (listeCase[ligne][0].case.contains(r.centerX(), r.centerY())
                && r.centerX()<listeCase[ligne][0].posX)
    }

    fun gele() {
        estGele = true
    }

    fun prendDegats(degats: Int) {
        pv-=degats
        if(pv<=0){
            mort = true
        }
    }
}
