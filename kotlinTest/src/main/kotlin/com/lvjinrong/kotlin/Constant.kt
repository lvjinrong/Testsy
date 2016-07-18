package com.lvjinrong.kotlin

import android.content.Context
import android.content.Intent

/**
 * Created by lvjinrong on 2016/6/22.
 * this is a singleton
 * 常量类。。。类似static ...就是一个单例
 * function
 */
object Constant {
    val name = "what you want.."
    var name2 = ""

    fun action( clazz: Class<Any> , context: Context ){
        context.startActivity(Intent(context , clazz))
    }

    fun <T> actiony( clazz: Class<T> , context: Context ){
        context.startActivity(Intent(context , clazz))
    }

}
