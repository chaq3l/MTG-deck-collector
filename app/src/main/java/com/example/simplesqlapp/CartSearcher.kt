package com.example.simplesqlapp


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplesqlapp.Adapter.RecycleViewCartDeckAdapter
import com.example.simplesqlapp.Adapter.RecycleViewSearchCartAdapter
import com.example.simplesqlapp.DBHelper.DBCartSearcher
import com.example.simplesqlapp.Model.Cart
import kotlinx.android.synthetic.main.activity_cart_finder.*


class CartSearcher : AppCompatActivity() {

    private var cartsInDeckLayoutManager: RecyclerView.LayoutManager? = null
    internal lateinit var db: DBCartSearcher
    internal var lstFoundCart: List<Cart> = ArrayList<Cart>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actualPhrase = intent.getStringExtra("searchingPhrase")
//        val actualDisplayedDeckIdINT =Integer.parseInt(actualDisplayedDeckId1)-1
//        val actualDisplayedDeckId = actualDisplayedDeckIdINT.toString()
        setContentView(R.layout.activity_cart_finder)
        db = DBCartSearcher(this, actualPhrase)
        val intent = intent

        refreshData(recyclerView, actualPhrase)

        button_to_main.setOnClickListener{
            val intent = Intent(this@CartSearcher, MainActivity::class.java)
            startActivity(intent)
        }

        search_button.setOnClickListener{
            val anotherSearchingPhrase = txt_search_again.text.toString()

            if (anotherSearchingPhrase==""){

                //nic nie wpisano


            }else{

                db = DBCartSearcher(this, anotherSearchingPhrase)
                lstFoundCart = db.searchedCartList

                if (lstFoundCart.isEmpty()){
                    //no results, user need to find angain
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("No results")
                    builder.setMessage("Please check if the correct data has been entered")
                    builder.setNeutralButton("Ok") { _: DialogInterface?, _: Int ->  }
                    builder.show()


                }else{
                    if (lstFoundCart.size==1){
                        //while found only one cart
                        val cartId= lstFoundCart[0].cardDbId.toString()
                        val intent = Intent(this@CartSearcher, SingleCartContent::class.java)
                        intent.putExtra("actualCartId", cartId)
                        startActivity(intent)


                    }else
                    {
                        //while found manny carts

                        refreshData(recyclerView, anotherSearchingPhrase)

                    }
                }
            }

        }


    }

    private fun refreshData(mRecyclerView : RecyclerView, actualPhrase:String) {
        mRecyclerView.setHasFixedSize(true)
        cartsInDeckLayoutManager = LinearLayoutManager(this)
        lstFoundCart = db.searchedCartList


        mRecyclerView.adapter = RecycleViewSearchCartAdapter(this@CartSearcher, lstFoundCart)
        mRecyclerView.layoutManager = cartsInDeckLayoutManager

        (mRecyclerView.adapter as RecycleViewSearchCartAdapter).setOnItemClickListener(object : RecycleViewSearchCartAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                showCart(position)
            }

        })

    }


    fun showCart(position: Int) {

        val rememberCartClicked:String = lstFoundCart[position].cardDbId.toString()
        val intent2 = Intent(this@CartSearcher, SingleCartContent::class.java)
        intent2.putExtra("actualCartId", rememberCartClicked)
        startActivity(intent2)
        //refreshData(mRecyclerView, actualDisplayedDeckIdInt)
    }

}
