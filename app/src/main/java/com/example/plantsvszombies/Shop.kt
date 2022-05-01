package com.example.plantsvszombies

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Shop (var credit: Credit, var x1 : Float, var y1: Float, var x2: Float, var y2: Float) {
    val r = RectF(x1, y1, x2, y2)

    var plantex = 0f
    var plantelargeur = 0f
    var plantelongueur = 0f

    var ty = 0f
    val tr = RectF(plantex-plantelongueur/2,ty-plantelargeur/2,plantex+plantelongueur/2,ty+plantelargeur/2)
    val prix_tournesol = App.instance.resources.getInteger(R.integer.prix_tournesol)
    var tsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_carte_shop)

    var pvy = 0f
    val pvr = RectF(plantex-plantelongueur/2,pvy-plantelargeur/2,plantex+plantelongueur/2,pvy+plantelargeur/2)
    val prix_planteVerte = App.instance.resources.getInteger(R.integer.prix_planteVerte)
    var pvsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.verte_carte_shop)

    var pgy = 0f
    val pgr = RectF(plantex-plantelongueur/2,pgy-plantelargeur/2,plantex+plantelongueur/2,pgy+plantelargeur/2)
    val prix_planteGlace = App.instance.resources.getInteger(R.integer.prix_planteGlace)
    var pgsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.glace_carte_shop)

    var ny = 0f
    val nr = RectF(plantex-plantelongueur/2,ny-plantelargeur/2,plantex+plantelongueur/2,ny+plantelargeur/2)
    val prix_noix = App.instance.resources.getInteger(R.integer.prix_noix)
    var nsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.noix_carte_shop)

    var by = 0f
    val br = RectF(plantex-plantelongueur/2,by-plantelargeur/2,plantex+plantelongueur/2,by+plantelargeur/2)
    val prix_buche = App.instance.resources.getInteger(R.integer.prix_buche)
    var bsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche_carte_shop)

    lateinit var plante_touchee : String
    var modeAchat = false


    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(tr.contains(xtouch, ytouch) && this.achetable("Tournesol")){
            plante_touchee = "Tournesol"
            modeAchat = true
            reset()
            tsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_carte_shop_click)
        }
        if(pvr.contains(xtouch, ytouch) && this.achetable("Plante_verte")){
            plante_touchee = "Plante_verte"
            modeAchat = true
            reset()
            pvsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.verte_carte_shop_click)
        }
        if(pgr.contains(xtouch, ytouch) && this.achetable("Plante_glace")){
            plante_touchee = "Plante_glace"
            modeAchat = true
            reset()
            pgsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.glace_carte_shop_click)
        }
        if(nr.contains(xtouch, ytouch) && this.achetable("Noix")){
            plante_touchee = "Noix"
            modeAchat = true
            reset()
            nsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.noix_carte_shop_click)
        }
        if(br.contains(xtouch, ytouch) && this.achetable("Buche")){
            plante_touchee = "Buche"
            modeAchat = true
            reset()
            bsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche_carte_shop_click)
        }

    }

    fun draw(canvas : Canvas){
        canvas.drawBitmap(tsprite, null, tr.toRect(), null)
        canvas.drawBitmap(pvsprite, null, pvr.toRect(), null)
        canvas.drawBitmap(pgsprite, null, pgr.toRect(), null)
        canvas.drawBitmap(nsprite, null, nr.toRect(), null)
        canvas.drawBitmap(bsprite, null, br.toRect(), null)
    }

    fun set(){
        r.set(x1, y1, x2, y2)
        tr.set(plantex-plantelongueur/2,ty-plantelargeur/2,plantex+plantelongueur/2,ty+plantelargeur/2)
        pvr.set(plantex-plantelongueur/2,pvy-plantelargeur/2,plantex+plantelongueur/2,pvy+plantelargeur/2)
        pgr.set(plantex-plantelongueur/2,pgy-plantelargeur/2,plantex+plantelongueur/2,pgy+plantelargeur/2)
        nr.set(plantex-plantelongueur/2,ny-plantelargeur/2,plantex+plantelongueur/2,ny+plantelargeur/2)
        br.set(plantex-plantelongueur/2,by-plantelargeur/2,plantex+plantelongueur/2,by+plantelargeur/2)
    }

    fun achetable(plante : String): Boolean {
        var achetable = false

        when(plante){
            "Tournesol" -> achetable = prix_tournesol <= credit.credit
            "Plante_verte" -> achetable = prix_planteVerte <= credit.credit
            "Plante_glace" -> achetable = prix_planteGlace <= credit.credit
            "Noix" -> achetable = prix_noix <= credit.credit
            "Buche" -> achetable = prix_buche <= credit.credit
        }

        return achetable
    }
    fun reset() {
         tsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_carte_shop)
         pvsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.verte_carte_shop)
         pgsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.glace_carte_shop)
         nsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.noix_carte_shop)
         bsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche_carte_shop)
    }
}

