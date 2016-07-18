package com.lvjinrong.kotlin

/**
 * Created by lvjinrong on 2016/6/22.
 */
data class User(val name: String , val pass: String = "no pass")

data class Friend(val name: String , val sex : Int ?= 0 , val phone: String , val married : Boolean ?= false)

 data class Enemy(val id : Int){
     var name : String ?= null
 }

class Frid{
    var name: String? = null
    var sex: Int? = null
    private var age: Int? = null
}

class BaseMessage {
    var percent: Float = 0.toFloat()
    var content: String? = null
    var color: Int = 0
}

