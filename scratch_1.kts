image_search.setOnClickListener {

    if (cart_name_input.text.toString()==""){

        //nic nie wpisano


    }else{
        val inputPhrase = cart_name_input.text.toString()
        dbSearcher = DBCartSearcher(this, inputPhrase)
        lstFoundCart = dbSearcher.searchedCartList

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
                val cartId= lstFoundCart[0].id.toString()
                val intent = Intent(this@MainActivity, SingleCartContent::class.java)
                intent.putExtra("actualCartId", cartId)
                startActivity(intent)



            }else
            {
                //while found manny carts

                val intent = Intent(this@MainActivity, CartSearcher::class.java)
                intent.putExtra("searchingPhrase", inputPhrase)
                startActivity(intent)

            }
        }
    }
}