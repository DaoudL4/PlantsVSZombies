package com.example.plantsvszombies

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.toRect
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.exp
import kotlin.random.Random

class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback {
    lateinit var canvas : Canvas
    var drawing = false
    lateinit var thread : Thread

    var cases : Array<Array<Case>>
    val ncaseY = 5
    val ncaseX = 9
    var largeurCase = 0f
    var longueurCase = 0f
    var distanceCaseX = 0f
    var distanceCaseY = 0f
    var credit = Credit(0f,0f,0f)
    var shop = Shop(credit, 0f, 0f, 0f, 0f)
    var soleil = Soleil(credit, 0f,0f,0f)
    var pelle = Pelle(0f,0f,0f)
    var plantes = ConcurrentLinkedQueue<Plante>()
    var zombies = ArrayList<Zombie>()
    var barreProgression = BarreProgression(0f,0f,0f,0f)
    var spawn = SpawnZombie(this)

    var t0 = 0L
    val tempsPartie = 120

    var gameOver = false
    val activity = context as FragmentActivity

    init {
        cases = Array(ncaseY){Array(ncaseX){Case(0f, 0f, 0f, 0f, this)} }
        t0 = System.currentTimeMillis()
    }

    override fun onTouchEvent(e: MotionEvent): Boolean{
        if(e.action == MotionEvent.ACTION_DOWN){
            soleil.onTouch(e)
            shop.onTouch(e)
            pelle.onTouch(e)

            if(shop.modeAchat || pelle.destruction) {
                for (i in 0..ncaseY - 1) {
                    for (j in 0..ncaseX - 1) {
                        cases[i][j].onTouch(e, shop.modeAchat, pelle.destruction)
                    }
                }
            }
        }
        return true
    }


    fun draw(){
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas.drawColor(0, PorterDuff.Mode.CLEAR)


            for (i in 0..ncaseY-1){
                for (j in 0..ncaseX-1){
                    cases[i][j].draw(canvas)
                }
            }


            val iterator = plantes.iterator()
            while(iterator.hasNext()){
                val p = iterator.next()
                p.draw(canvas)
            }


            shop.draw(canvas)
            credit.draw(canvas)
            soleil.draw(canvas)
            pelle.draw(canvas)
            for (z in zombies) {
                z.draw(canvas)
            }
            barreProgression.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun achatPlante(case: Case) {
        val plante = shop.plante_touchee.nom
        var cout = 0
        when(plante){
            "Tournesol" -> {
                cout = resources.getInteger(R.integer.prix_tournesol)
                plantes.add(Tournesol(case, 75f, soleil))
            }
            "Plante_verte" -> {
                cout = resources.getInteger(R.integer.prix_planteVerte)
                plantes.add(Plante_verte(case, 75f, zombies, plantes))
            }
            "Plante_glace" -> {
                cout = resources.getInteger(R.integer.prix_planteGlace)
                plantes.add(Plante_glace(case, 75f, zombies, plantes))
            }
            "Noix" -> {
                cout = resources.getInteger(R.integer.prix_noix)
                plantes.add(Noix(case, 75f))
            }
            "Buche" -> {
                cout = resources.getInteger(R.integer.prix_buche)
                plantes.add(Buche(case, 75f))
            }
        }
        shop.plante_touchee.actif = false
        shop.plante_touchee.resetTimer()

        for (z in zombies) {
            z.listeCase = cases
        }
        credit.updateCredit(-cout)
        shop.modeAchat = false
        case.plante = plantes.elementAt(plantes.size-1)
    }

    fun detruitPlante(case: Case) {
        plantes.removeAll{it.case == case}
        pelle.destruction = false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        largeurCase = 2*w/27.toFloat()
        longueurCase = h/6.toFloat()
        distanceCaseX = 5*w/18.toFloat()
        distanceCaseY = h/12.toFloat()+largeurCase
        for (i in 0..ncaseY-1){
            for (j in 0..ncaseX-1){
                cases[i][j].pair = ((j+1)+i*ncaseX)%2
                cases[i][j].largeur = largeurCase
                cases[i][j].longueur = longueurCase
                cases[i][j].posX = distanceCaseX + largeurCase*(j+(1/2))
                cases[i][j].posY = distanceCaseY + longueurCase*(i+(1/2))
                cases[i][j].setcase()
            }
        }

        shop.plantelargeur = longueurCase
        shop.plantelongueur = 1.8f*shop.plantelargeur
        shop.x1 = 30f
        shop.y1 = longueurCase
        shop.x2 = shop.x1 + shop.plantelongueur
        shop.y2 = h.toFloat()
        shop.plantex = shop.x2/2

        for(i in 0..shop.elements.size-1){
            shop.elements[i].y = shop.y1 + i*(shop.plantelargeur+15f)
        }
        shop.set()

        soleil.x = shop.x2 + longueurCase
        soleil.y = 1.5f*longueurCase/2
        soleil.rayon = largeurCase/2
        soleil.set()

        credit.x = soleil.x
        credit.y = soleil.y + w/9
        credit.rayon = largeurCase/2
        credit.set()

        pelle.y = soleil.y
        pelle.x = soleil.x + 8*largeurCase
        pelle.rayon = largeurCase/2
        pelle.set()

        for (z in zombies) {
            z.set()
        }

        for(p in plantes){
            p.set()
        }

        barreProgression.x = w/2.toFloat()
        barreProgression.y = h/12.toFloat()
        barreProgression.longueur = 4*largeurCase
        barreProgression.largeur = largeurCase/3
        barreProgression.set()

    }

    override fun run() {
        var previousTime = System.currentTimeMillis()
        while(drawing) {
            val currentTime = System.currentTimeMillis()
            val interval = (currentTime-previousTime).toDouble()
            val t = (currentTime-t0).toDouble()

            zombies.removeAll{it.mort == true}
            plantes.removeAll{it.mort == true}

            draw()

            soleil.timer(currentTime)
            for(elt in shop.elements){elt.timer(currentTime)}
            spawn.setPeriode(t)
            spawn.timer(currentTime)

            for (z in zombies) {
                z.avance(interval)
            }

            for (p in plantes) {
                if (p is Plante_verte){
                    p.avanceBalles(interval)
                    if(currentTime - p.t0 > p.periode_tir*1000){
                        p.tir()
                    }
                }
                if (p is Plante_glace){
                    p.avanceBalles(interval)
                    if(currentTime - p.t0 > p.periode_tir*1000){
                        p.tir()
                    }
                }
            }

            barreProgression.progression = (t/(1000*tempsPartie)).toFloat()
            barreProgression.set()

            previousTime = currentTime

            if(t >= tempsPartie*1000) gameOver_win()
        }
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }



    fun newGame(){
        plantes = ConcurrentLinkedQueue<Plante>()
        zombies = ArrayList<Zombie>()
        credit.reset()
        soleil.reset()
        pelle.reset()
        for (i in 0..ncaseY-1){
            for (j in 0..ncaseX-1){
                cases[i][j].reset()
            }
        }
        t0 = System.currentTimeMillis()
        spawn.resetTimer()
        for(elt in shop.elements){elt.resetTimer()}

        drawing = true
        if(gameOver){
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }

    fun showGameOver(messageId: Int) {
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {

                val builder = AlertDialog.Builder(getActivity())
                builder.setTitle(resources.getString(messageId))
                builder.setMessage(
                    resources.getString(
                        R.string.results_format
                    )
                )
                builder.setPositiveButton(R.string.reset_game,
                    DialogInterface.OnClickListener { _, _->newGame()}
                )
                return builder.create()
            }
        }

        activity.runOnUiThread(
            Runnable {
                val ft = activity.supportFragmentManager.beginTransaction()
                val prev =
                    activity.supportFragmentManager.findFragmentByTag("dialog")
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)
                val gameResult = GameResult()
                gameResult.setCancelable(false)
                gameResult.show(ft,"dialog")
            }
        )
    }

    fun gameOver_win(){
        drawing = false
        gameOver = true
        showGameOver(R.string.win)
    }
    fun gameOver_lose(){
        drawing = false
        gameOver = true
        showGameOver(R.string.lose)
    }


}