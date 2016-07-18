package com.lvjinrong.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var mLevel: Int = 0
    private var mNextLevelButton: Button? = null
    private var mLevelTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = findViewById(R.id.next_level_button) as Button?
        mNextLevelButton!!.isEnabled = false
        mNextLevelButton!!.setOnClickListener { Toast.makeText(this, "mNextLevelButton Clicked...", Toast.LENGTH_LONG).show() }

        // Create the text view to show the level number.
        mLevelTextView = findViewById(R.id.level) as TextView?
        mLevel = START_LEVEL

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show()
    }

    /**
     *  companion object --- 伴随对象
     *  静态的属性、 常量或者函数， 我们可以使用 companion object
     *  这个对象被这个类的所有对象所共享， 就像Java中的静态属性或者方法。
     **/
    companion object {
        // Remove the below line after defining your own ad unit ID.
        private val TOAST_TEXT = "Test ads are being shown. " + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID."

        private val START_LEVEL = 1

        val TOAST_TXT = "Test Public Static"   // can be used directly
    }
}
