package com.example.plantsvszombies

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import androidx.core.graphics.toRect

class Shop (var credit: Credit, var x1 : Float, var y1: Float, var x2: Float, var y2: Float) {
    var shopPaint = Paint()
    val r = RectF(x1, y1, x2, y2)

    var tx = 0f
    var ty = 0f
    var trad = 0f
    val tr = RectF(tx-trad,ty-trad,tx+trad,ty+trad)
    val prix_tournesol = App.instance.resources.getInteger(R.integer.prix_tournesol)
    var tsprite = BitmapFactory.decodeResource(App.instance.resources, R.drawable.shop_tournesol)

    lateinit var plante_touchee : String
    var modeAchat = false

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100

        if(tr.contains(xtouch, ytouch) && this.achetable("Tournesol")){
            plante_touchee = "Tournesol"
            modeAchat = true
        }

    }

    fun draw(canvas : Canvas){
        shopPaint.color = Color.BLUE
        canvas.drawRect(r, shopPaint)
        canvas.drawBitmap(tsprite, null, tr.toRect(), null)
    }

    fun set(){
        r.set(x1, y1, x2, y2)
        tr.set(tx-trad,ty-trad,tx+trad,ty+trad)
    }

    fun achetable(plante : String): Boolean {
        var achetable = false

        when(plante){
            "Tournesol" -> achetable = prix_tournesol <= credit.credit
        }

        return achetable
    }
}

