package com.example.plantsvszombies

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

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
    var shop = Shop(0f, 0f, 0f, 0f)
    var soleil = Soleil(credit, 0f,0f,0f)

    var tournesol: Tournesol

    init {
        backgroundPaint.color = Color.WHITE
        cases = Array(ncaseY){Array(ncaseX){Case(0f, 0f, 0f, 0f)} }
        tournesol = Tournesol(cases[2][4], 0f)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean{
        if(e?.action == MotionEvent.ACTION_DOWN){
            soleil.onTouch(e)
            shop.onTouch(e)
        }
        return true
    }


    fun draw(){
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                canvas.height.toFloat(), backgroundPaint)

            for (i in 0..ncaseY-1){
                for (j in 0..ncaseX-1){
                    cases[i][j].draw(canvas)
                }
            }
            shop.draw(canvas)
            credit.draw(canvas)
            soleil.draw(canvas)
            tournesol.draw(canvas)

            holder.unlockCanvasAndPost(canvas)
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
        shop.trad = shop.y2/3
        shop.tx = shop.x1+shop.trad+15f
        shop.ty = shop.y2/2
        shop.set()

        credit.x = shop.x1/2
        credit.y = shop.y2/2
        credit.rayon = largeurCase/2
        credit.set()

        soleil.x = shop.x2 + shop.x1/2
        soleil.y = shop.y2/2
        soleil.rayon = largeurCase/2
        soleil.set()

        tournesol.rayon = largeurCase/2
        tournesol.set()
    }

    override fun run() {
        while(drawing) {
            draw()
            if(soleil.etat == false && System.currentTimeMillis() - soleil.t0 > soleil.periode*1000){
                soleil.changeEtat()
            }
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
}