package com.kapil.sunset

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.nio.Buffer

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
     protected fun ConvertStreamToString(inputStream: InputStream?):String {

//         println("******** Convert Stream To String ***********")
             var line = StringBuilder("")
             var allLine = StringBuilder("")
             val br = BufferedReader(InputStreamReader(inputStream))
         try {
             do {
                 line = StringBuilder(br.readLine())
                 allLine.append(line)
             } while (line != null)
         inputStream!!.close()


         }
         catch (ex:java.lang.Exception)
         {
             print(ex)
         }
//         println(allLine.toString())
return allLine.toString()
     }
        fun GetTemperature(view: android.view.View)
        {
            val city:String?= city_Name.getText().toString()

            val key:String?="b4175a484b11132fb673239b93f6d83c"
     val url="https://api.openweathermap.org/data/2.5/weather?q=$city&APPID=$key"
           MyAsyncTask().execute(url)

        }

       inner class MyAsyncTask: AsyncTask<String, String, String>() {
            override fun doInBackground(vararg p0: String?): String {
                //To do httpcall
                try {

//                    println("******** do In Background ***********")
                    println(p0[0])
                    val url = URL(p0[0])

                    val urlConnect=url.openConnection()
                    urlConnect.connectTimeout=7000

                   var inString=ConvertStreamToString(urlConnect.getInputStream())

                    publishProgress(inString)

                } catch (ex: Exception) {
             var intent=Intent(this@MainActivity,Error_handling::class.java)
                    this@MainActivity.startActivity(intent)
                        print("api error "+ex)
                }
                    return " "
            }




           override fun onPostExecute(result: String?) {

                super.onPostExecute(result)
            }

            override fun onPreExecute() {
                //  before start the task
                super.onPreExecute()
            }

            override fun onProgressUpdate(vararg values: String?) {
            try {

                val json=JSONObject(values[0])
                 val main=json.getJSONObject("main")

                var temp=main.getInt("temp")
           temp= (temp-273.15).toInt()
                tv_Weather.setText("Temperature in your city = "+temp+"°C")
            }
            catch(ex:java.lang.Exception)
            {
                println(ex)

            }







       }
    }




 }