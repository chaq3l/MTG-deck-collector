package com.example.simplesqlapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import android.support.v4.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import com.example.simplesqlapp.Adapter.ListCartAdapter
import com.example.simplesqlapp.Adapter.ListDeckAdapter
import com.example.simplesqlapp.DBHelper.DBHelper
import com.example.simplesqlapp.DBHelper.SingleCartDBHelper
import com.example.simplesqlapp.Model.Cart
import com.example.simplesqlapp.Model.CartDeck
import com.example.simplesqlapp.Model.Deck
import com.example.simplesqlapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_add_cart_to_deck
import kotlinx.android.synthetic.main.activity_single_cart_content.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

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
        val actualDeckId =  intent.getStringExtra("actualDeckId")
        db = SingleCartDBHelper(this, actualDeckId, Integer.parseInt(actualDisplayedCart))
        val actualCart = db.singleCart[0]

        val cartTitle = findViewById<TextView>(R.id.cart_title)
        cartTitle.text = actualCart.name
        val actualCartDescription = findViewById<TextView>(R.id.cart_description)
        actualCartDescription.text = actualCart.secondaryId
        btn_add_cart_to_deck.setOnClickListener {



        }


    }


        //Event


}

