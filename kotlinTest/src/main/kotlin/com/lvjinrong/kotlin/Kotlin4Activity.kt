package com.lvjinrong.kotlin

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kotlin4.*
import org.jetbrains.anko.toast
import java.util.*

class Kotlin4Activity : AppCompatActivity() , View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            kotlin4_btn1 -> {
                val map = mapOf("name" to "John Doe", "age"  to 25)
                val user = User( map )
                toast("name=${user.name},age=${user.age}")
            }
            kotlin4_btn2 -> toast("max:${6.compareMax(10)}")
            kotlin4_btn3 -> toast("tailRec....${findFixPoint(1.0)}")
        }
    }

    private val mList = mutableListOf<BaseMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin4)
        kotlin4_btn1.setOnClickListener(this)
        kotlin4_btn2.setOnClickListener(this)
        kotlin4_btn3.setOnClickListener(this)

        cakeView.setStartAngle(60.toFloat())
        cakeView.setTextColor(Color.parseColor("#000000"))

        for (i in 1..16){
            val mes = BaseMessage()
            mes.percent = (i + 20).toFloat()
            mes.content = "" + i
            mes.color = if (i % 2 == 0) Color.parseColor("#fff000") else Color.parseColor("#ff00ff")
            mList.add(mes)
        }

        cakeView.setCakeData(mList)

        val list = asList(1,2,3,4,5,6)


        val doublelist = list.map { it -> it * 2 }

        val strings = listOf("anim-v21" , "interpolator" , "layout-sw600dp" , "layout-sw600dp-v13" )
        val maxValue = max(strings, { a, b -> a.length < b.length })

        fun(x: Int, y: Int): Int = x + y

    }



    fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
        var max: T? = null
        for (it in collection)
            if (max == null || less(max, it))
                max = it
        return max
    }

    /**
     *
     */
    fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this)
            result.add(transform(item))
        return result
    }

    /**
     * vararg 可变长度的参数列表
     */
    fun <T> asList(vararg ts: T): List<T> {
        val result = ArrayList<T>()
        for (t in ts) // ts is an Array
            result.add(t)
        return result
    }

    /**
     * 函数为成员函数或扩展函数 函数只有一个参数函数用infix关键字标记
     */
    infix fun Int.compareMax(int: Int): Int = if (this.toInt() > int) this.toInt() else int

    /**
     * Kotlin supports a style of functional programming known as tail recursion.
     * This allows some algorithms that would normally be written using loops to instead be written using a recursive function,
     * but without the risk of stack overflow.
     * When a function is marked with the tailrec modifier and meets the required form the compiler optimises out the recursion,
     * leaving behind a fast and efficient loop based version instead.
     * 尾递归式
     */
    tailrec fun findFixPoint(x: Double = 1.0): Double = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

    class User( map: Map<String, Any?> ) {
        val name: String by map
        val age: Int     by map
    }


}
