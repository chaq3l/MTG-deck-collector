package com.example.simplesqlapp

//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
//import android.support.v4.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.simplesqlapp.DBHelper.SingleCartDBHelper


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

        val addCartToDeckButton = findViewById<Button>(R.id.btn_add_cart_to_deck)

        addCartToDeckButton.setOnClickListener {

            val intent = Intent(this@SingleCartContent, AddChosenCartToDeckContent::class.java)
            intent.putExtra( "actualCartId", actualDisplayedCart)
            startActivity(intent)


        }


    }


        //Event


}

