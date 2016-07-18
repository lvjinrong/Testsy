package com.lvjinrong.kotlin

/**
 * Created by lvjinrong on 2016/7/4.
 */




open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4  // public by default

    protected open class Nested {
        val e: Int = 5   // default == public
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    override val b = 5   // 'b' is protected

    init {
        println(c)
        val nested = Nested()
        print(nested.e)
    }
}


open class C

class D: C()

fun C.foo() = "c"

fun D.foo() = "d"

fun printFoo(c: C) {
    println(c.foo())
}


