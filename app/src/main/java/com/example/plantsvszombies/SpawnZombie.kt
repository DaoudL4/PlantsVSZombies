package com.example.plantsvszombies

import android.util.Log
import kotlin.math.exp
import kotlin.random.Random

class SpawnZombie(val view : GameView) : Timer{
    override var t0Timer = 0L
    override var periodeTimer = 0f

    var level = 0
    var a = 0
    var b = 0
    var c = 0


    override fun actionTimer() {
        val tirage = Random.nextInt(0,100)
        if(tirage<70) view.zombies.add(Zombie(Random.nextInt(0,view.ncaseY), 200f, view.cases, view))
        else if (tirage > 97)view.zombies.add(Zombie_magicien(Random.nextInt(0,view.ncaseY), 200f, view.cases, view))
        else view.zombies.add(Zombie_cone(Random.nextInt(0,view.ncaseY), 200f, view.cases, view))
        resetTimer()
    }

    fun setPeriode(t : Double){
        periodeTimer = (a + b* exp(-t/c*1000)).toFloat()
    }

    fun setLevel(){
        when(level){
            0 -> {
                a = 3
                b = 12
                c = 50
            }
            1 -> {
                a = 3
                b = 12
                c = 50
            }
            2 -> {
                a = 0
                b = 1
                c = 1
            }
        }
    }


}