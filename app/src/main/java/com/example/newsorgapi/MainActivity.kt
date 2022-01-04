package com.example.newsorgapi

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapiretrofit.Article
import com.example.newsapiretrofit.News
import com.example.newsorgapi.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var adapter: Adapter
    val viewModel: Mainviewmodel by viewModels()
    var tempne : MutableList<Article> = ArrayList()
    var tempnene : MutableList<Article> = ArrayList()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initviewmodel("in")
        binding.loc.setOnClickListener {
            Alertcountry()
        }
    }

    private fun Alertcountry() {
        var selectedcountryindex = 0
        val countrylist = arrayOf("India", "USA")

        var selectedcountry = countrylist[selectedcountryindex]

        MaterialAlertDialogBuilder(this)
            .setTitle("Select Country")
            .setSingleChoiceItems(countrylist, selectedcountryindex){dialog, which ->
                selectedcountryindex = which
                selectedcountry = countrylist[which]
            }
            .setPositiveButton("Ok"){dialog, which ->
                Toast.makeText(this, "$selectedcountry selected", Toast.LENGTH_SHORT).show()
                if(selectedcountry == "USA"){
                    initviewmodel("us")
                }
                else{
                    initviewmodel("in")
                }
            }
            .setNeutralButton("Cancel"){dialog, which ->

            }
            .show()
    }

    private fun initviewmodel(selcountry : String) {
        viewModel.getLiveDataobserver().observe(this, Observer {
            if(it!= null){
                viewModel.selectedcountry = selcountry
                tempne.clear()
                tempnene.clear()
                tempne.addAll(it)
                tempnene.addAll(tempne)
                showdata(tempnene)
            }
            else{
                Toast.makeText(this, "Error in view model", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }

    fun showdata(news: List<Article>){

        adapter = Adapter(this, news)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menuitems, menu)

        val item = menu.findItem(R.id.search)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {

                tempnene.clear()
                val searchtext = newText!!.lowercase(Locale.getDefault())
                if(searchtext.isNotEmpty()){

                    tempne.forEach {

                        if(it.description.lowercase(Locale.getDefault()).contains(searchtext)){

                            tempnene.add(it)
                        }
                    }
                    binding.recycler.adapter!!.notifyDataSetChanged()
                }
                else{

                    tempnene.clear()
                    tempnene.addAll(tempne)
                    binding.recycler.adapter!!.notifyDataSetChanged()
                }


                return true
            }


        })

        return super.onCreateOptionsMenu(menu)
    }
}