package com.example.plantsvszombies

import android.graphics.*


class Tournesol(case: Case, rayon : Float, soleil: Soleil) : Plante(case, rayon) {
    override val sprite = BitmapFactory.decodeResource(
        App.instance.resources,
        R.drawable.tournesol2
    )
    override var pv = 3000f
    init {
        soleil.changeFreq(1.2f)
    }

}