package com.dicoding.wecare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.wecare.models.LoginResponse
import com.dicoding.wecare.storage.SharedPrefManager
import kotlinx.android.synthetic.main.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        login_btn.setOnClickListener{


            val nik = inputNIK.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if(nik.isEmpty()){
                inputNIK.error = "Nik required"
                inputNIK.requestFocus()
                return@setOnClickListener
            }


            if(password.isEmpty()){
                inputPassword.error = "Password required"
                inputPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(nik, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(response.body()?.error == false){

                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                            startActivity(intent)

                        }
                        else{
                            Toast.makeText(applicationContext, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
                        }


                    }
                })

        }
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

}