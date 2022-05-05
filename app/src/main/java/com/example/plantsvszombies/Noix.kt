package com.example.plantsvszombies

import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.core.graphics.toRect

class Noix(case: Case, rayon: Float) : Plante(case, rayon) {
    override val sprite = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.sprite_noix
    )
    override var pv = 40000f
    val pv0 = pv

    val sprite_phase2 = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.sprite_noix_2
    )
    val sprite_phase3 = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.sprite_noix_3
    )

    override fun draw(canvas: Canvas){
        if(pv/pv0 > 0.6){
            super.draw(canvas)
        }
        else if(pv/pv0 > 0.3 && pv/pv0 < 0.6){
            canvas.drawBitmap(sprite_phase2, null, r.toRect(), null)
        }
        else {
            canvas.drawBitmap(sprite_phase3, null, r.toRect(), null)
        }
    }
}