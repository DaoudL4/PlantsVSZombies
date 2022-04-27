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

    var px = 0f
    val pr = RectF(px-planterad,plantey-planterad,px+planterad,plantey+planterad)
    val prix_planteVerte = App.instance.resources.getInteger(R.integer.prix_planteVerte)
    var psprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.sprite_verte_shop)

    lateinit var plante_touchee : String
    var modeAchat = false

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(tr.contains(xtouch, ytouch) && this.achetable("Tournesol")){
            plante_touchee = "Tournesol"
            modeAchat = true
        }
        if(pr.contains(xtouch, ytouch) && this.achetable("Plante_verte")){
            plante_touchee = "Plante_verte"
            modeAchat = true
        }

    }

    fun draw(canvas : Canvas){
        shopPaint.color = Color.BLUE
        canvas.drawRect(r, shopPaint)
        canvas.drawBitmap(tsprite, null, tr.toRect(), null)
        canvas.drawBitmap(psprite, null, pr.toRect(), null)
    }

    fun set(){
        r.set(x1, y1, x2, y2)
        tr.set(tx-planterad,plantey-planterad,tx+planterad,plantey+planterad)
        pr.set(px-planterad,plantey-planterad,px+planterad,plantey+planterad)
    }

    fun achetable(plante : String): Boolean {
        var achetable = false

        when(plante){
            "Tournesol" -> achetable = prix_tournesol <= credit.credit
            "Plante_verte" -> achetable = prix_planteVerte <= credit.credit
        }

        return achetable
    }
}

