package com.example.plantsvszombies


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.exp
import kotlin.random.Random

class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), Runnable, SurfaceHolder.Callback {
    lateinit var canvas : Canvas
    private var drawing = false
    private lateinit var thread : Thread

    var cases : Array<Array<Case>>
    val ncaseY = 5
    val ncaseX = 9

    private var largeurCase = 0f
    private var longueurCase = 0f
    private var distanceCaseX = 0f
    private var distanceCaseY = 0f
    private var credit = Credit(0f,0f,0f)
    private var shop = Shop(credit, 0f, 0f, 0f, 0f)
    private var soleil = Soleil(credit, 0f,0f,0f)
    private var pelle = Pelle(0f,0f,0f)
    var plantes = ConcurrentLinkedQueue<Plante>()
    var zombies = ArrayList<Zombie>()
    private var barreProgression = BarreProgression(0f,0f,0f,0f)
    private var spawn = SpawnZombie(this)

    private var t0 = 0L
    private val tempsPartie = 240
    var arcade = false

    private var gameOver = false
    private val activity = context as FragmentActivity

    init {
        this.visibility = View.VISIBLE
        val level = activity.intent.extras!!.getInt("level")
        spawn.level = level
        spawn.setLevel()
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
            if(!arcade) barreProgression.draw(canvas)
            else barreProgression.drawTime(canvas)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun achatPlante(case: Case) {
        val plante = shop.plante_touchee.nom
        var cout = 0
        when(plante){
            "Tournesol" -> {
                cout = resources.getInteger(R.integer.prix_tournesol)
                plantes.add(Tournesol(case, 50f, soleil))
            }
            "Plante_verte" -> {
                cout = resources.getInteger(R.integer.prix_planteVerte)
                plantes.add(Plante_verte(case, 50f, this))
            }
            "Plante_glace" -> {
                cout = resources.getInteger(R.integer.prix_planteGlace)
                plantes.add(Plante_glace(case, 50f, this))
            }
            "Noix" -> {
                cout = resources.getInteger(R.integer.prix_noix)
                plantes.add(Noix(case, 50f))
            }
            "Buche" -> {
                cout = resources.getInteger(R.integer.prix_buche)
                plantes.add(Buche(case, 50f))
            }
        }
        shop.plante_touchee.actif = false
        shop.plante_touchee.resetTimer()
        shop.resetSelect()

        case.plante = plantes.elementAt(plantes.size-1)

        for (z in zombies) {
            z.listeCase = cases
        }
        credit.updateCredit(-cout)
        shop.modeAchat = false
    }

    fun detruitPlante(case: Case) {
        plantes.removeAll{it.case == case}
        pelle.destruction = false
    }

    fun spawnZombie(n : Int){
        when(n) {
            1 -> zombies.add(Zombie(Random.nextInt(0,ncaseY), 100f, cases, this))
            2 -> zombies.add(Zombie_cone(Random.nextInt(0,ncaseY), 100f, cases, this))
            3 -> zombies.add(Zombie_mage(Random.nextInt(0,ncaseY), 100f, cases, this))
        }
    }

    fun spawnMain(case : Case){
        plantes.add(ZombieMain(case, 50f))
        case.plante = plantes.elementAt(plantes.size-1)
        case.occupe = true
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

        soleil.x = shop.x2 + w/20
        soleil.y = 1.5f*longueurCase/2
        soleil.rayon = largeurCase/2
        soleil.set()

        credit.x = soleil.x
        credit.y = soleil.y + h/7
        credit.rayon = largeurCase/2
        credit.set()

        pelle.y = soleil.y
        pelle.x = soleil.x + 9 *largeurCase
        pelle.rayon = largeurCase/2
        pelle.set()


        for (z in zombies) {
            z.rayon = h/10.toFloat()
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
            for(elt in shop.elements){
                elt.timer(currentTime)
                elt.setProgression()
                elt.set()
            }
            spawn.setPeriode(t)
            spawn.timer(currentTime)


            for (z in zombies) {
                z.avance(interval)
                if(z is Zombie_mage) z.timer(currentTime)
            }

            for (p in plantes) {
                if (p is Plante_verte){
                    p.avanceBalles(interval)
                    p.timer(currentTime)
                }
                if (p is Plante_glace){
                    p.avanceBalles(interval)
                    p.timer(currentTime)
                }
            }

            barreProgression.progression = (t/(1000*tempsPartie)).toFloat()
            barreProgression.t = (t/1000).toFloat()
            barreProgression.set()

            previousTime = currentTime

            if(t >= tempsPartie*1000 && !arcade) gameOver_win()
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

        this.visibility = View.VISIBLE


        drawing = true
        if(gameOver){
            gameOver = false
            thread = Thread(this)
            thread.start()
        }
    }

    private fun showGameOver(message: String, imageId : Int) {
        activity.runOnUiThread(
            Runnable {
                val gameResult = GameOverFragment(message, this, imageId)

                val ft = activity.supportFragmentManager.beginTransaction()
                ft.setReorderingAllowed(true)
                ft.add(R.id.fragment_container, gameResult, "gameoverfragment")
                this.visibility = View.INVISIBLE
                ft.commit()

            }
        )
    }

    private fun gameOver_win(){
        drawing = false
        gameOver = true
        showGameOver(resources.getString(R.string.win), R.drawable.tournesol_sprite)
    }
    fun gameOver_lose(){
        drawing = false
        gameOver = true
        showGameOver(resources.getString(R.string.lose), R.drawable.lose_zomboss)
    }


}