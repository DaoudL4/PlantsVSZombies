package com.example.plantsvszombies

import android.graphics.Canvas

abstract class Plante(val case : Case) {
    open val recharge = 100
    open val cout = 100

    open fun draw(canvas : Canvas){}
}