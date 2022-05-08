package com.example.plantsvszombies

import android.graphics.*
import android.view.MotionEvent

class Shop (private var credit: Credit, var x1 : Float, var y1: Float, var x2: Float, var y2: Float) {
    private val r = RectF(x1, y1, x2, y2)

    var plantex = 0f
    var plantelargeur = 0f
    var plantelongueur = 0f

    var elements = ArrayList<ShopElement>()

    lateinit var plante_touchee : ShopElement
    var modeAchat = false

    init {
        elements.add(ShopElement(this, plantex, 0f, plantelongueur, plantelargeur, "Tournesol",App.instance.resources.getInteger(R.integer.prix_tournesol),App.instance.resources.getInteger(R.integer.periode_tournesol).toFloat() ,BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_carte_shop), BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_carte_shop_click), BitmapFactory.decodeResource(App.instance.resources, R.drawable.tournesol_carte_shop_select)))
        elements.add(ShopElement(this, plantex, 0f, plantelongueur, plantelargeur, "Plante_verte", App.instance.resources.getInteger(R.integer.prix_planteVerte),App.instance.resources.getInteger(R.integer.periode_planteVerte).toFloat(),BitmapFactory.decodeResource(App.instance.resources, R.drawable.verte_carte_shop), BitmapFactory.decodeResource(App.instance.resources, R.drawable.verte_carte_shop_click), BitmapFactory.decodeResource(App.instance.resources, R.drawable.verte_carte_shop_select)))
        elements.add(ShopElement(this, plantex, 0f, plantelongueur, plantelargeur, "Plante_glace",App.instance.resources.getInteger(R.integer.prix_planteGlace),App.instance.resources.getInteger(R.integer.periode_planteGlace).toFloat() ,BitmapFactory.decodeResource(App.instance.resources, R.drawable.glace_carte_shop), BitmapFactory.decodeResource(App.instance.resources, R.drawable.glace_carte_shop_click), BitmapFactory.decodeResource(App.instance.resources, R.drawable.glace_carte_shop_select)))
        elements.add(ShopElement(this, plantex, 0f, plantelongueur, plantelargeur, "Noix",App.instance.resources.getInteger(R.integer.prix_noix),App.instance.resources.getInteger(R.integer.periode_noix).toFloat() ,BitmapFactory.decodeResource(App.instance.resources, R.drawable.noix_carte_shop), BitmapFactory.decodeResource(App.instance.resources, R.drawable.noix_carte_shop_click), BitmapFactory.decodeResource(App.instance.resources, R.drawable.noix_carte_shop_select)))
        elements.add(ShopElement(this, plantex, 0f, plantelongueur, plantelargeur, "Buche", App.instance.resources.getInteger(R.integer.prix_buche),App.instance.resources.getInteger(R.integer.periode_buche).toFloat(),BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche_carte_shop), BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche_carte_shop_click), BitmapFactory.decodeResource(App.instance.resources, R.drawable.buche_carte_shop_select)))
    }

    fun onTouch(e : MotionEvent){
        val xtouch = e.rawX - 100
        val ytouch = e.rawY - 100


        for (elt in elements) {
            elt.onTouch(xtouch, ytouch)
        }

    }

    fun achat(element: ShopElement) {
        plante_touchee = element
        modeAchat = true
    }

    fun draw(canvas : Canvas){
        for (elt in elements){
            elt.draw(canvas)
        }
    }

    fun set(){
        r.set(x1, y1, x2, y2)
        for(elt in elements){
            elt.x = plantex
            elt.plantelargeur = plantelargeur
            elt.plantelongueur = plantelongueur
            elt.set()}
    }

    fun achetable(prix : Int): Boolean {
        return prix <= credit.credit
    }

    fun resetSelect(){
        for (elt in elements){
            elt.select = false
        }
    }
}

