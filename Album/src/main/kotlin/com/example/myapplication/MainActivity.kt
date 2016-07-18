package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

import com.example.bean.ImageBean
import com.example.bean.ImageBucket

import java.util.ArrayList

/**
 * @author peng
 */
class MainActivity : AppCompatActivity() {
    //显示手机里所有图片的控件
    private var gridView: GridView? = null
    //当手机里没有图片时，提示手机没有图片的控件
    private var textView: TextView? = null
    //GridView的适配器
    private var adapter: AlbumGridviewAdapter? = null
    private var context: Context? = null
    private var dataList: MutableList<ImageBean>? = null
    private var contentList: ArrayList<ImageBucket>? = null
    private var helper: AlbumHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.plugin_camera_no_pictures)
        init()
        initListener()
    }


    //初始化给对象赋值
    private fun init() {
        helper = AlbumHelper.helper
        helper!!.init(applicationContext)
        contentList = helper!!.getImagesBucketList(false) as ArrayList<ImageBucket>
        dataList = ArrayList<ImageBean>()
        for (i in contentList!!.indices) {
            (dataList as MutableList<ImageBean>).addAll(contentList!![i].imageList!!)
        }
        Log.v("----", "" + dataList!!.size)
        intent = getIntent()
        val bundle = intent!!.extras
        gridView = findViewById(R.id.grid) as GridView?
        adapter = AlbumGridviewAdapter(this, dataList as ArrayList<ImageBean>, Bimp.tempSelectBitmap)
        (adapter as AlbumGridviewAdapter).setOnItemClickListener { toggleButton, i, b, button ->
            Log.v("----","----" + i)
        }
        gridView!!.adapter = adapter
        textView = findViewById(R.id.myText) as TextView?
        gridView!!.emptyView = textView
        (gridView as GridView).setOnItemClickListener ({ adapterView, view, i, l ->

        });

    }

    private fun initListener() {
        adapter!!.setOnItemClickListener({
            view, position , isChecked  , chooseBt ->
            if (Bimp.tempSelectBitmap.size >= PublicWay.num) {
                view.isChecked = false
                chooseBt.visibility = View.GONE
                return@setOnItemClickListener
            }
            if (isChecked) {
                chooseBt.visibility = View.VISIBLE
                Bimp.tempSelectBitmap.add(dataList!![position])
            } else {
                Bimp.tempSelectBitmap.remove(dataList!![position])
                chooseBt.visibility = View.GONE
            }
        })
    }

}
