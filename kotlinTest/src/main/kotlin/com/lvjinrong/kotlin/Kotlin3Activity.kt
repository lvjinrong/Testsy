package com.lvjinrong.kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTabHost
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.PopupWindow
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_kotlin3.*
import org.jetbrains.anko.toast
import java.io.File

class Kotlin3Activity : AppCompatActivity(), CustomRecyclerView.OnItemClickListener {
    override fun onItemClick(parent: RecyclerView, itemView: View, index: Int) {
        toast("parentId...${parent.id},itemView...${itemView.background},index....$index")
        println(lazyValue)
    }

    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )
    private val items1 = null
    /**
     * val -- read-only
     * var -- mutable
     */
    private val str = "4"
    private val a: Int = 24
    private var b: Int = 24
    private var c: Int = 0

    private val TAG: String = Kotlin3Activity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin3)

        b = 32
        c = Integer.parseInt(str)
        if(c in 0..items.lastIndex){
            toast("in...")
        }else toast("out...")

        val map = mapOf("a" to 1 , "b" to 2 , "c" to 3 )
        println("just test ==== " + map["a"] )

        items1?.let {   // items not null execute...
            toast("items not null...")
        }

        Kotlin_rv1.layoutManager = LinearLayoutManager(this)
        Kotlin_rv1.adapter = Kotlin3Adapter(items)
        Kotlin_rv1.setOnItemClickListener(this)

        val listener = object : Kotlin3Adapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
            //    toast("C...pos....$position")
                "hello everyone".extendWhatsUP()
            }
        }

        Kotlin_rv2.layoutManager = LinearLayoutManager(this)
        Kotlin_rv2.adapter = Kotlin3Adapter(items)
        (Kotlin_rv2.adapter as Kotlin3Adapter).setOnItemClickListener( listener )

        val text = """|Tell me and I forget.|Teach me and I remember.|Involve me and I learn.|(Benjamin Franklin)""".trimMargin()
        println(text)

        for ((index, value) in items.withIndex()) {
            println("the element at $index is $value")
        }

        println("loop continue begin...")
        loop@ for (i in 1..9) {
            for (j in 1..9) {
                if (i < j){
                    println()
                    continue@loop
                } else print("$i * $j = ${i * j} ")
            }
        }
        println()
        println("loop continue finish...")

        items.forEach {
            if(it.contains("wed" , true)) return
            println(it)
        }
      //  items.forEachIndexed { i, s ->  }
        val frid = Frid()
        frid.name = "what"

    }

    fun sum0( a: Int , b: Int) : Int = a + b
    fun sum1( a: Int , b: Int) = a + b
    fun sum2( a: Int , b: Int){ println(a + b) }
    fun max0( a: Int , b: Int) = if (a > b) a else b
    fun max1( a: Int , b: Int) :Int{
        if(a > b){
            return a
        } else return b
    }

    /**
     * we can use this function
     * like foo() || foo(1) , but cannot foo("")
     */
    fun foo(a: Int? = 0, b: String = "") { toast("...$a....$b") }

    fun String.extendWhatsUP(){
        //here is in string , just used
        toast(TAG + "===---" +this.toString() + "...." +
                this.length )  // just this string
      //  foo(null , "d")
    }

}
