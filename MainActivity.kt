package com.interview.mywallst_assignment.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.interview.mywallst_assignment.R
import com.interview.mywallst_assignment.data_payload.MeteorsApiResponse
import com.interview.mywallst_assignment.data_payload.MeteorsApiResponseItem
import com.interview.mywallst_assignment.ui.InterfaceNasaAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import kotlin.math.roundToInt


const val BASE_URL = "https://data.nasa.gov/resource/"

class MainActivity : AppCompatActivity() {

    private val RESPONSE_TAG = MainActivity::class.qualifiedName
    private var responseList: MeteorsApiResponse? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListRecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleSwipeRefresh()
        if (checkNetwork())
            createRetrofitBuilder()
        else {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to check if internet connection is available or not
    private fun checkNetwork(): Boolean {
        var isConnected: Boolean = false
        val connManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNet: NetworkInfo? = connManager.activeNetworkInfo
        isConnected = activeNet?.isConnectedOrConnecting == true

        return isConnected

    }

    // Method to handle "Pull to Refresh"
    private fun handleSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            if (checkNetwork())
                createRetrofitBuilder()
            else {
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun createRetrofitBuilder() {
        val retrofitObj = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InterfaceNasaAPI::class.java)

        //Coroutines for Asynchronous Data Handling
        enableCoRoutines(retrofitObj)
    }

    private fun enableCoRoutines(retrofitObj: InterfaceNasaAPI) {

        GlobalScope.launch {

            // I/O Thread Execution (Async)
            Dispatchers.IO {

                //Try Catch block is to check if response is according to original data payload
                try {
                    val response = retrofitObj.getAPIResponse()

                    //check if API is working and resource url is found
                    if (response.isSuccessful) {
                        Log.i(RESPONSE_TAG, response.body().toString()!!)

                        if (response.body().isNullOrEmpty()) {
                            Dispatchers.Main {
                                emptyResponseToast(response)
                                swipeRefreshLayout.isRefreshing = false
                            }
                        } else {
                            responseList = response.body() as MeteorsApiResponse
                            val meteorList = arrayListOf<MeteorsApiResponseItem?>()

                            applyFilters(responseList!!, meteorList)

                            // Sort by Maximum Size (mass) value first
                            meteorList!!.sortByDescending { it!!.mass.toInt() + 1 }

                            Dispatchers.Main {
                                displayResults(meteorList)
                                swipeRefreshLayout.isRefreshing = false
                            }
                        }
                    } else {
                        Log.e(RESPONSE_TAG, response.toString())
                        Dispatchers.Main {
                            serverErrorToast(response)
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                } catch (e: Exception) {
                    Log.e(RESPONSE_TAG, e.toString())
                    Dispatchers.Main {
                        exceptionToast(e.toString())
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
        }
    }

    // Method to validate item value in payload
    private fun applyFilters(responseList: MeteorsApiResponse, meteorList: ArrayList<MeteorsApiResponseItem?>)
    {
        for (i in 0 until responseList!!.size) {
            val mass = responseList!![i].mass
            if (!mass.isNullOrEmpty()) {
                try {
                    // eg: 1022000 to 102000
                    responseList!![i].mass = mass.toInt().toString()
                    checkForYear(responseList!!, meteorList, i)
                } catch (e: Exception) {
                    // eg: 1020.6 to 1021
                    responseList!![i].mass = mass.toFloat().roundToInt().toString()
                    checkForYear(responseList!!, meteorList, i)
                }
            }
        }
    }

    // Method to Initialize and Display results in recyclerview
    private fun displayResults(meteorList: ArrayList<MeteorsApiResponseItem?>) {

        val listSize = meteorList!!.size.toString()
        list_size_tv.text =
            "WOW! Found $listSize records after applying filters. \n Tap to see on Map"
        linearLayoutManager = LinearLayoutManager(this@MainActivity)
        meteors_rv.layoutManager = linearLayoutManager
        listAdapter = ListRecyclerAdapter(meteorList!!)
        meteors_rv.adapter = listAdapter
        listAdapter.notifyDataSetChanged()
    }

    // Method to Check if Year of Fallen Meteor is more than 1900
    fun checkForYear(
        responseList: MeteorsApiResponse,
        meteorList: ArrayList<MeteorsApiResponseItem?>,
        i: Int
    ): Boolean {
        var checkPassed = false

        // This Elvis Operator required because response contains null values
        val year = responseList!![i].year ?: "0000-00-00T00:00:00.000"

        if (formatYearFromResponse(year).toInt() > 1900) {
            meteorList.add(responseList!![i])
            checkPassed = true
        }
        return checkPassed
    }

    //Method to extract year from response item for comparison
    fun formatYearFromResponse(year: String): String {

        var result: String = "Date format Incorrect"
        try {
            var parser = SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss")
            var formatter = SimpleDateFormat("yyyy")
            result = formatter.format(parser.parse(year))
        } catch (e: Exception) {
            Log.e("ERROR_TAG", e.toString())
            return result
        }

        return result
    }

    // Method to Display error when response is valid but empty
    private fun emptyResponseToast(response: Response<MeteorsApiResponse>) {
        Toast.makeText(
            this@MainActivity,
            "Data Not Found: " + response.message(),
            Toast.LENGTH_SHORT
        ).show()
    }

    //Method to Display API Error Message
    private fun exceptionToast(e: String) {
        Toast.makeText(
            this@MainActivity,
            "WIP - Please try after some time - $e",
            Toast.LENGTH_LONG
        ).show()
    }

    // Method to Display Server Error Message
    private fun serverErrorToast(response: Response<MeteorsApiResponse>) {
        Toast.makeText(
            this@MainActivity,
            response.message() + " - Code- " + response.code(),
            Toast.LENGTH_SHORT
        ).show()
    }
}