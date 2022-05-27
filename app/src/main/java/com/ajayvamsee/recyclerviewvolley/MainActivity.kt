package com.ajayvamsee.recyclerviewvolley

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var userArrayList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var userRV: RecyclerView
    private lateinit var loadingPB: ProgressBar
    private lateinit var nestedSV: NestedScrollView

    private var page = 0
    private val limit = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userArrayList = ArrayList()

        userRV = findViewById(R.id.idRVUsers)
        loadingPB = findViewById(R.id.idPBLoading)
        nestedSV = findViewById(R.id.idNestedSV)

        getDataFromAPI()

        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                loadingPB.visibility = View.VISIBLE

                getDataFromAPI()
            }
        })
    }

    private fun getDataFromAPI() {
        if (page > limit) {
            Toast.makeText(this, "That's the all the data...", Toast.LENGTH_SHORT).show()
            loadingPB.visibility = View.GONE
            return
        }

        val url = "https://reqres.in/api/users?page=$page"

        val queue: RequestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val dataArray = response.getJSONArray("data")

                    for (i in 0 until dataArray.length()) {
                        val jsonObject = dataArray.getJSONObject(i)
                        userArrayList.add(
                            User(
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                jsonObject.getString("email"),
                                jsonObject.getString("avatar")
                            )
                        )

                        userAdapter = UserAdapter(userArrayList, this)

                        userRV.layoutManager = LinearLayoutManager(this)

                        userRV.adapter = userAdapter
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }

            },
            {
                Toast.makeText(this, "Fail to get data..", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)
    }
}