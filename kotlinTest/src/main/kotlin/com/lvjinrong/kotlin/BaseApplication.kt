package com.lvjinrong.kotlin

import android.app.Activity
import android.app.Application
import java.util.Stack

/**
 * Created by lvjinrong on 2016/4/27.
 */
class BaseApplication : Application() {

    var activityStack = Stack<Activity>()

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext as BaseApplication
    }


    fun addActivity(activity: Activity) {
        activityStack!!.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack!!.remove(activity)
    }

    companion object {
        var instance: BaseApplication? = null
            private set    // 私有set方法,公有get方法
    }

}
