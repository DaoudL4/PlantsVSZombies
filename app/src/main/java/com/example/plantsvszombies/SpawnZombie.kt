package com.example.plantsvszombies

import kotlin.math.exp
import kotlin.random.Random

class SpawnZombie(val view : GameView) : Timer{
    override var t0Timer = 0L
    override var periodeTimer = 0f

    override fun actionTimer() {
        if(Random.nextInt(0,100)<80) view.zombies.add(Zombie(Random.nextInt(0,view.ncaseY), 75f, view.cases, view))
        else view.zombies.add(Zombie_cone(Random.nextInt(0,view.ncaseY), 100f, view.cases, view))
        resetTimer()
    }

    fun setPeriode(t : Double){
        periodeTimer = (3 + 12* exp(-t/50000)).toFloat()
    }


}