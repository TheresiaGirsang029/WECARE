package com.dicoding.wecare

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.wecare.models.*
import com.dicoding.wecare.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.konsultasi.*
import kotlinx.android.synthetic.main.menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ImageButton

class ProfileActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        logout.setOnClickListener {
            SharedPrefManager.getInstance(applicationContext).clear()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        }

        val name_user = SharedPrefManager.getInstance(applicationContext).user.name.toString()
        val nik_user = SharedPrefManager.getInstance(applicationContext).user.nik.toString()
        txtname.setText(name_user)
        txtumur.setText(nik_user)

        val id = SharedPrefManager.getInstance(applicationContext).user.id
//        Jumlah Pengaduan User
        RetrofitClient.instance.getDataFromAPI(id)
            ?.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    val counts = response.body()?.count
                    if(response.body()?.error == false){
                        txtCount.setText(counts)
                    }else{
                        txtCount.setText("0")
                    }
                }
            })

//        Jumlah Pengaduan Success
        RetrofitClient.instance.getOk(id)
            ?.enqueue(object: Callback<PengaduanOk> {
                override fun onFailure(call: Call<PengaduanOk>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<PengaduanOk>, response: Response<PengaduanOk>) {

                    val count2 = response.body()?.countOk
                    if(response.body()?.error == false){
                        txtCount2.setText(count2)
                    }else{
                        txtCount2.setText("0")
                    }
                }
            })

//        Jumlah Pengaduan tidak Diterima
        RetrofitClient.instance.getNotOk(id)
            ?.enqueue(object: Callback<PengaduanNotOk> {
                override fun onFailure(call: Call<PengaduanNotOk>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<PengaduanNotOk>, response: Response<PengaduanNotOk>) {

                    val count3 = response.body()?.countNotOk
                    if(response.body()?.error == false){
                        txtCount3.setText(count3)
                    }else{
                        txtCount3.setText("0")
                    }

                }
            })


//        button Konsultasi
        menu2.setOnClickListener {
            setContentView(R.layout.konsultasi)
        }

//        button chatbot
        menu3.setOnClickListener({
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://34.101.228.44/we-care/User_Page/Users_page_c#hubungi"))
            startActivity(i)
        })

//        Button Police Call
        menu5.setOnClickListener {
            setContentView(R.layout.policecall)
        }


    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }




}