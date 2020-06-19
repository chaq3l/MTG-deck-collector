package com.example.simplesqlapp

//import android.support.v7.app.AppCompatActivity
//import android.support.v4.app.ActivityCompat

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.simplesqlapp.DBHelper.SingleCartDBHelper
import java.io.InputStream
import java.net.URL


//import org.json.JSONArray
//import java.io.IOException
//import java.io.InputStream
//import android.widget.ArrayAdapter


class SingleCartContent : AppCompatActivity() {
    //28:00

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == REQUEST_READ_EXTERNAL) parseJason()
//    }



    internal lateinit var db: SingleCartDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_cart_content)
        val actualDisplayedCart =  intent.getStringExtra("actualCartId")


        db = SingleCartDBHelper(this, Integer.parseInt(actualDisplayedCart))
        val actualCart = db.singleCart[0]

        val cartTitle = findViewById<TextView>(R.id.cart_title)
        cartTitle.text = actualCart.name
        val actualCartDescription = findViewById<TextView>(R.id.cart_description)
        actualCartDescription.text = actualCart.cartText
        val cardImageURL = actualCart.cartImageUris
        val addCartToDeckButton = findViewById<Button>(R.id.btn_add_cart_to_deck)

//        val cardBitmap = loadImageFromWebOperations(actualCart.cartImageUris)?.let {
////            drawableToBitmap(
////                it
////            )
////        }


        //potrzebny permission na uzycie linku do wczytania obrazka "android.permission.INTERNET"
        val cardFace = findViewById<ImageView>(R.id.cart_image)
        cardFace.setOnClickListener{

            actualCart.cartImageUris?.let { runImageUIThread(it, cardFace) }
        }

        //cart_image.setImageBitmap(bitmap)

        addCartToDeckButton.setOnClickListener {

            val intent = Intent(this@SingleCartContent, AddChosenCartToDeckContent::class.java)
            intent.putExtra( "actualCartId", actualDisplayedCart)
            startActivity(intent)


        }

    }


    private fun runImageUIThreadBitmapLoadOnly(url:String): Bitmap? {
        var bitmap :Bitmap? = null
        var thread = Thread(Runnable {
            try {
                Log.d("Image url", url)
                bitmap = (loadImageFromWebOperations(url)?.let {
                    drawableToBitmap(
                        it
                    )
                } )


            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()
        return bitmap
    }

    private fun runImageUIThread(url:String, cardFace:ImageView){
        var thread = Thread(Runnable {
            try {
                Log.d("Image url", url)
                val bitmap = (loadImageFromWebOperations(url)?.let {
                    drawableToBitmap(
                        it
                    )
                } )

                cardFace.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()

    }


    private fun loadImageFromWebOperations(url: String?): Drawable? {
        val image: InputStream = URL(url).content as InputStream
        return Drawable.createFromStream(image, "srcName")

        }


    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    }




