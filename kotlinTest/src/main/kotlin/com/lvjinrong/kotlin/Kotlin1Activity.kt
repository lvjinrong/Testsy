package com.lvjinrong.kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kotlin1.*
import org.jetbrains.anko.toast

/**
 * ---> !! non-null
 * ---> ?  null or not null ...safe
 * var ( can be changed )
 * val  just as final in java
 *
 * ScrollView.setOnScrollChangeListener must be supported from api23
 *
 *
 * */
class Kotlin1Activity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        val child : LinearLayout = Kotlin_sv.getChildAt(0) as LinearLayout
        when(v!!.id){
          R.id.Kotlin_Btn1 -> {
              Toast.makeText(this, "Kotlin_Btn1 Clicked..." + child.childCount, Toast.LENGTH_LONG).show()
          }
          R.id.Kotlin_Btn2 ->{
           //   Toast.makeText(this,"Kotlin_Btn2 Clicked..." + child.childCount , Toast.LENGTH_LONG).show()
              startActivity(Intent(this@Kotlin1Activity,Kotlin2Activity::class.java))
          }
          R.id.Kotlin_Btn3 ->{
           //   Toast.makeText(this,"Kotlin_Btn3 Clicked..." + child.childCount , Toast.LENGTH_LONG).show()
              startActivity(Intent(this@Kotlin1Activity,Kotlin3Activity::class.java))
          }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin1)
        Kotlin_Btn1.setOnClickListener(this)
        Kotlin_Btn2.setOnClickListener(this)
        Kotlin_Btn3.setOnClickListener(this)


    }


}
