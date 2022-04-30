package com.example.plantsvszombies

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.toRect
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.random.Random

class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback {
    lateinit var canvas : Canvas
    val backgroundPaint = Paint()
    var drawing = false
    lateinit var thread : Thread

    var cases : Array<Array<Case>>
    val ncaseY = 3
    val ncaseX = 9
    var largeurCase = 0f
    var longueurCase = 0f
    var distanceCaseX = 0f
    var distanceCaseY = 0f
    var credit = Credit(0f,0f,0f)
    var shop = Shop(credit, 0f, 0f, 0f, 0f)
    var soleil = Soleil(credit, 0f,0f,0f)
    var plantes = ConcurrentLinkedQueue<Plante>()
    var zombies = ArrayList<Zombie>()
    val periodeSpawnZombie = 7
    var spawnt0 = 0L

    var gameOver = false
    val activity = context as FragmentActivity

    init {
        cases = Array(ncaseY){Array(ncaseX){Case(0f, 0f, 0f, 0f, this)} }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean{
        if(e.action == MotionEvent.ACTION_DOWN){
            soleil.onTouch(e)
            shop.onTouch(e)

            if(shop.modeAchat) {
                for (i in 0..ncaseY - 1) {
                    for (j in 0..ncaseX - 1) {
                        cases[i][j].onTouch(e)
                    }
                }
            }
        }
        return true
    }


    fun draw(){
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas.drawBitmap(BitmapFactory.decodeResource(App.instance.resources, R.drawable.background)
            , null, RectF(0f, 0f, canvas.width.toFloat(),
                    canvas.height.toFloat()).toRect(), null)


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
            for (z in zombies) {
                z.draw(canvas)
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun achatPlante(case: Case) {
        val plante = shop.plante_touchee
        val cout : Int
        when(plante){
            "Tournesol" -> {
                cout = resources.getInteger(R.integer.prix_tournesol)
                plantes.add(Tournesol(case, 75f, soleil))
                credit.updateCredit(-cout)
                shop.modeAchat = false
                for (z in zombies) {
                    z.listeCase = cases
                }
            }
            "Plante_verte" -> {
                cout = resources.getInteger(R.integer.prix_planteVerte)
                plantes.add(Plante_verte(case, 100f, zombies, plantes))
                credit.updateCredit(-cout)
                shop.modeAchat = false
                for (z in zombies) {
                    z.listeCase = cases
                }
            }
            "Plante_glace" -> {
                cout = resources.getInteger(R.integer.prix_planteGlace)
                plantes.add(Plante_glace(case, 75f, zombies, plantes))
                credit.updateCredit(-cout)
                shop.modeAchat = false
                for (z in zombies) {
                    z.listeCase = cases
                }
            }
            "Noix" -> {
                cout = resources.getInteger(R.integer.prix_noix)
                plantes.add(Noix(case, 75f))
                credit.updateCredit(-cout)
                shop.modeAchat = false
                for (z in zombies) {
                    z.listeCase = cases
                }
            }
            "Buche" -> {
                cout = resources.getInteger(R.integer.prix_buche)
                plantes.add(Buche(case, 75f))
                credit.updateCredit(-cout)
                shop.modeAchat = false
                for (z in zombies) {
                    z.listeCase = cases
                }
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        largeurCase = 2*w/27.toFloat()
        longueurCase = h/6.toFloat()
        distanceCaseX = w/3.toFloat()
        distanceCaseY = h/2.toFloat()
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

        shop.x1 = w/6.toFloat()
        shop.y1 = 0f
        shop.x2 = shop.x1 + 6*largeurCase
        shop.y2 = 1.5f*longueurCase
        shop.planterad = shop.y2/3
        shop.plantey = shop.y2/2
        shop.tx = shop.x1+shop.planterad+15f
        shop.pvx = shop.x1+shop.planterad+15f+largeurCase
        shop.pgx = shop.x1+shop.planterad+15f+2*largeurCase
        shop.nx = shop.x1+shop.planterad+15f+3*largeurCase
        shop.bx = shop.x1+shop.planterad+15f+4*largeurCase
        shop.set()

        credit.x = shop.x1/2
        credit.y = shop.y2/2
        credit.rayon = largeurCase/2
        credit.set()

        soleil.x = shop.x2 + shop.x1/2
        soleil.y = shop.y2/2
        soleil.rayon = largeurCase/2
        soleil.set()

        for (z in zombies) {
            z.set()
        }

        for(p in plantes){
            p.set()
        }

    }

    override fun run() {
        var previousTime = System.currentTimeMillis()
        while(drawing) {
            val currentTime = System.currentTimeMillis()
            val interval = (currentTime-previousTime).toDouble()

            draw()
            for (z in zombies) {
                z.avance(interval)
            }

            if(!soleil.etat && currentTime - soleil.t0 > soleil.periode*1000){
                soleil.changeEtat()
            }

            if(currentTime - spawnt0 > periodeSpawnZombie*1000){
                if(Random.nextInt(0,100)<80) zombies.add(Zombie(Random.nextInt(0,3), 75f, cases, this))
                else zombies.add(Zombie_cone(Random.nextInt(0,3), 100f, cases, this))
                spawnt0 = currentTime
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
            previousTime = currentTime

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

    /*
    fun newGame(){
        plantes = ArrayList<Plante>()
        credit.reset()
        soleil.reset()
        for (i in 0..ncaseY-1){
            for (j in 0..ncaseX-1){
                cases[i][j].reset()
            }
        }

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
        showGameOver(R.string.win)
        gameOver = true
    }
    fun gameOver_lose(){
        drawing = false
        showGameOver(R.string.lose)
        gameOver = true
    }

     */
}