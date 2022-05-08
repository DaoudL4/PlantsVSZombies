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
        when {
            tirage<70 -> view.zombies.add(Zombie(Random.nextInt(0,view.ncaseY), 100f, view.cases, view))
            tirage > 97 -> view.zombies.add(Zombie_mage(Random.nextInt(0,view.ncaseY), 100f, view.cases, view))
            else -> view.zombies.add(Zombie_cone(Random.nextInt(0,view.ncaseY), 100f, view.cases, view))
        }
        resetTimer()
    }

    fun setPeriode(t : Double){
        periodeTimer = (a + b* exp(-t/c*1000)).toFloat()
    }

    fun setLevel(){
        when(level){
            0 -> {
                a = 5
                b = 15
                c = 60
            }
            1 -> {
                a = 3
                b = 12
                c = 50
            }
            2 -> {
                a = 1
                b = 13
                c = 50
            }
        }
    }


}