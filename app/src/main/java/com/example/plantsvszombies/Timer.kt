package com.example.plantsvszombies

interface Timer {
    var t0Timer : Long
    var periodeTimer : Float

    fun actionTimer()
    fun timer(currentTime : Long){
        if(currentTime-t0Timer > periodeTimer*1000){
            actionTimer()
        }
    }
    fun resetTimer(){
        t0Timer = System.currentTimeMillis() 
    }
}