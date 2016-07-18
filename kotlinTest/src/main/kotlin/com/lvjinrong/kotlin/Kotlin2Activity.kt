package com.lvjinrong.kotlin

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.Html.ImageGetter
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.content_kotlin2.*
import org.jetbrains.anko.toast
import java.io.InputStream
import java.net.URL
import java.util.*

class Kotlin2Activity : AppCompatActivity() , View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.Kotlin2_btn1 -> {
                val map = HashMap<String, String>()
                map.put("key1", "value1")
                map.put("key2", "value2")
                map.put("key3", "value3")
                map.put("key4", "value4")
                for ((key, value) in map) {
                    Log.v("map....print...", "key:$key , value:$value")
                }
            }
            R.id.Kotlin2_btn2 ->{
                val user1 = User("handsome", "pass")
                val user2 = User("house")
                Log.v("user....pass...", "user:${user1.name} , value:${user1.pass}")
                Log.v("user....pass...", "user:${user2.name} , value:${user2.pass}")
            }
            R.id.Kotlin2_btn3 ->{
                val list = listOf( 1, 2, 3, 4, 5, 6, 7, 8)
                Log.v("-" + list.any { it % 2 == 0 },"" + list.any { it > 10 })
                Log.v("--" + list.all { it < 10 },"" + list.all { it % 2 == 0 })
                println( list.count { it % 2 == 0 } )
                Log.v("----" + list.fold(0){total, next -> total + next } , "" + list.foldRight(0){total , next -> total + next})
                list.forEach { println(it) }
                list.forEachIndexed { index, value -> println( "position $index contains a $value") }
                println(list.max())   // maxValue
                println(list.maxBy { -it })  // minValue
                println(list.min())
                println(list.minBy { -it })
            }
            R.id.Kotlin2_btn4 ->{
            //    for(i in 0..10) println(i)    // 0 1 2 3 4 5 6 7 8 9 10
            //    for ( i in 10.. 0) println(i)     // nothing
            //    for(i in 10 downTo 0 step 2) println(i) // 10 8 6 4 2 0
            //    for ( i in 0 until 4) println(i)   // 0 1 2 3
            //    for ( i in 1..4 step 2) println(i)     // 1 3
                println( Constant.name + "----" + Constant.name2 )

                val enemy = Enemy(1)
                enemy.name = "suck dick"
                if(enemy.name == null) println("nothing...") else println(enemy.name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin2)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener(View.OnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()

        })
        Kotlin2_btn1.setOnClickListener(this)
        Kotlin2_btn2.setOnClickListener(this)
        Kotlin2_btn3.setOnClickListener(this)
        Kotlin2_btn4.setOnClickListener(this)

        Thread(){
            run {
                val html = "<h1>this is TextView</h1>" + "<p>This text is normal</p>" + "<img src='http://www.linuxidc.com/upload/2013_10/131019103614651.jpg' />";
                val sp = Html.fromHtml(html, ImageGetter {
                    source ->
                    Log.d(".....", "start.....")
                    val ins = URL(source).content as InputStream
                    val drawable = Drawable.createFromStream(ins, "src")
                    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                    ins.close()
                    Log.d(".....", "finish....")
                    return@ImageGetter drawable
                }, null)
                runOnUiThread {
                    Kotlin_tv.movementMethod = ScrollingMovementMethod.getInstance()
                    Kotlin_tv.text = sp
                }
            }
        }.start()

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0-> toast("B...${data?.getIntExtra("test3", 0)}"+requestCode)
        }
    }

}
