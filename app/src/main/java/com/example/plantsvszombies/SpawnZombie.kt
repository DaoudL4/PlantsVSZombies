package com.example.plantsvszombies

import kotlin.math.exp
import kotlin.random.Random

class SpawnZombie(private val view : GameView) : Timer{
    override var t0Timer = 0L
    override var periodeTimer = 0f

    var level = 0
    private var a = 0
    private var b = 0
    private var c = 0


    override fun actionTimer() {
        val tirage = Random.nextInt(0,100)
        var n = 0
        when {
            tirage<70 -> n = 1
            tirage > 90 -> n = 2
            else -> n = 3
        }
        view.spawnZombie(n)
        resetTimer()
    }

    fun setPeriode(t : Double){
        periodeTimer = (a + b* exp(-t/(c*1000))).toFloat()
    }

    fun setLevel(){
        when(level){
            0 -> {
                a = 3
                b = 20
                c = 50
            }
            1 -> {
                a = 1
                b = 10
                c = 80
            }
            2 -> {
                view.arcade = true
                a = 1
                b = 30
                c = 30
            }
        }
    }


}