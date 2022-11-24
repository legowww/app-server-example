package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.domain.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text_view_result)

        val call = RetrofitBuilder.api.getTimeRoute(
            LocationCoordinate("126.6709157", "37.4056074", "126.63652", "37.37499041"))
        call.enqueue(object : Callback<List<TimeRoute>> {
            override fun onResponse(
                call: Call<List<TimeRoute>>,
                response: Response<List<TimeRoute>>
            ) {
                if (!response.isSuccessful) {
                    textView.text = "Code: " + response.code() + "???"
                    return
                }

                val timeRoutes = response.body()
                if (timeRoutes != null) {
                    for (timeRoute in timeRoutes) {
                        var start = "현재위치"
                        var end = timeRoute.route.lastEndStation

                        textView.append("나갈시간:${timeRoute.time}\n총 소요시간:${timeRoute.route.totalTime}분\n")
                        textView.append("출발지:$start 목적지:$end\n")

                        val tr = timeRoute.route.transportationList
                        tr.forEachIndexed { i, t ->
                            val trafficType = t.trafficType
                            if (trafficType.equals("WALK")) {
                                textView.append("[도보] ${t.time}분\n")
                            }
                            else if (trafficType.equals("BUS")) {
                                textView.append("[버스] ${t.time}분 (${t.busNum}번 ${t.startName} -> ${t.endName})\n")
                            }
                        }
                        textView.append("\n")
                    }
                }
            }

            override fun onFailure(call: Call<List<TimeRoute>>, t: Throwable) {
                textView.text = t.message
            }
        })
    }
}










