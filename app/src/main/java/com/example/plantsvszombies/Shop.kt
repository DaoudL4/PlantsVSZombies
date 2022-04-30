package com.example.plantsvszombies

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Shop (var credit: Credit, var x1 : Float, var y1: Float, var x2: Float, var y2: Float) {
    var shopPaint = Paint()
    val r = RectF(x1, y1, x2, y2)

    var plantey = 0f
    var planterad = 0f

    var tx = 0f
    val tr = RectF(tx-planterad,plantey-planterad,tx+planterad,plantey+planterad)
    val prix_tournesol = App.instance.resources.getInteger(R.integer.prix_tournesol)
    var tsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.shop_tournesol)

    var pvx = 0f
    val pvr = RectF(pvx-planterad,plantey-planterad,pvx+planterad,plantey+planterad)
    val prix_planteVerte = App.instance.resources.getInteger(R.integer.prix_planteVerte)
    var pvsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sprite_verte_shop)

    var pgx = 0f
    val pgr = RectF(pgx-planterad,plantey-planterad,pgx+planterad,plantey+planterad)
    val prix_planteGlace = App.instance.resources.getInteger(R.integer.prix_planteGlace)
    var pgsprite = BitmapFactory.decodeResource(App.instance.resources,
        R.drawable.plante_glace_shop
    )

    var nx = 0f
    val nr = RectF(nx-planterad,plantey-planterad,nx+planterad,plantey+planterad)
    val prix_noix = App.instance.resources.getInteger(R.integer.prix_noix)
    var nsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sprite_noix_shop)

    lateinit var plante_touchee : String
    var modeAchat = false

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(tr.contains(xtouch, ytouch) && this.achetable("Tournesol")){
            plante_touchee = "Tournesol"
            modeAchat = true
        }
        if(pvr.contains(xtouch, ytouch) && this.achetable("Plante_verte")){
            plante_touchee = "Plante_verte"
            modeAchat = true
        }
        if(pgr.contains(xtouch, ytouch) && this.achetable("Plante_glace")){
            plante_touchee = "Plante_glace"
            modeAchat = true
        }
        if(nr.contains(xtouch, ytouch) && this.achetable("Noix")){
            plante_touchee = "Noix"
            modeAchat = true
        }

    }

    fun draw(canvas : Canvas){
        shopPaint.color = Color.BLUE
        canvas.drawRect(r, shopPaint)
        canvas.drawBitmap(tsprite, null, tr.toRect(), null)
        canvas.drawBitmap(pvsprite, null, pvr.toRect(), null)
        canvas.drawBitmap(pgsprite, null, pgr.toRect(), null)
        canvas.drawBitmap(nsprite, null, nr.toRect(), null)
    }

    fun set(){
        r.set(x1, y1, x2, y2)
        tr.set(tx-planterad,plantey-planterad,tx+planterad,plantey+planterad)
        pvr.set(pvx-planterad,plantey-planterad,pvx+planterad,plantey+planterad)
        pgr.set(pgx-planterad,plantey-planterad,pgx+planterad,plantey+planterad)
        nr.set(nx-planterad,plantey-planterad,nx+planterad,plantey+planterad)
    }

    fun achetable(plante : String): Boolean {
        var achetable = false

        when(plante){
            "Tournesol" -> achetable = prix_tournesol <= credit.credit
            "Plante_verte" -> achetable = prix_planteVerte <= credit.credit
            "Plante_glace" -> achetable = prix_planteGlace <= credit.credit
            "Noix" -> achetable = prix_noix <= credit.credit
        }

        return achetable
    }
}

