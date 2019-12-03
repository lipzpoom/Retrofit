package com.example.week7

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var progerssProgressDialog: ProgressDialog
    var dataList = ArrayList<datamodel>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:MyAdapter
    var context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.result)
        progerssProgressDialog=ProgressDialog(context)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()


        getData()
    }

    private fun getData() {
        val call: Call<List<datamodel>> = ApiClient.getClient.getPhotos()
        call.enqueue(object : Callback<List<datamodel>> {

            override fun onResponse(call: Call<List<datamodel>>?, response: Response<List<datamodel>>?) {
               Log.d("Response",Gson()?.toJson(response))
                recyclerView.adapter= MyAdapter(dataList,context)
                recyclerView.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                progerssProgressDialog.dismiss()
                dataList.addAll(response!!.body()!!)
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<datamodel>>?, t: Throwable?) {
                progerssProgressDialog.dismiss()
               Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
                Log.e("Load Error",t?.message)
            }

        })
    }

}
