package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.example.myapplication.BitmapCache
import com.example.bean.ImageBean

import java.util.ArrayList

/**
 * Created by Administrator on 2016/6/15 0015.
 */
class AlbumGridviewAdapter(private val mContext: Context, private val dataList: ArrayList<ImageBean>?, private val SelectedDataList: ArrayList<ImageBean>) : BaseAdapter() {
    internal val TAG = javaClass.simpleName
    private val dm: DisplayMetrics   //此类用来获取屏幕信息
    internal var cache: BitmapCache


    init {
        cache = BitmapCache()
        dm = DisplayMetrics()
        //获取屏幕宽和高
        (mContext as Activity).windowManager.defaultDisplay.getMetrics(dm)
    }

    override fun getCount(): Int {
        return dataList!!.size
    }

    override fun getItem(position: Int): Any {
        return dataList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

/*
    internal var callback: BitmapCache.ImageCallback = BitmapCache.ImageCallback {
        imageView, bitmap, params ->
        if (imageView != null && bitmap != null) {
            val url = params[0] as String
            if (url != null && url == imageView.tag as String) {
                imageView.setImageBitmap(bitmap)
            } else {
                Log.e(TAG, "callback, bmp not match")
            }
        } else {
            Log.e(TAG, "callback, bmp null")
        }
    }
    */

    /**
     * 存放列表项控件句柄
     */
    private inner class ViewHoler {
        var imageView: ImageView? = null
        var toggleButton: ToggleButton? = null
        var choosetoggle: Button? = null
        var textView: TextView? = null


    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHoler: ViewHoler
        if (convertView == null) {
            viewHoler = ViewHoler()
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout, null)
            viewHoler.imageView = convertView!!.findViewById(R.id.imageview) as ImageView
            viewHoler.toggleButton = convertView.findViewById(R.id.toggle_button) as ToggleButton
            viewHoler.choosetoggle = convertView.findViewById(R.id.choosedbt) as Button
            convertView.tag = viewHoler
        } else {
            viewHoler = convertView.tag as ViewHoler
        }

        var path: String
        if (dataList != null && dataList.size > position) {
            path = dataList[position].imagePath
        } else {
        }
        path = "camera_default"

        if (path.contains("camera_default")) {
            viewHoler.imageView!!.setImageResource(R.drawable.plugin_camera_no_pictures)
        } else {
            val item = dataList!![position]
            viewHoler.imageView!!.tag = item.imagePath
        //    cache.displayBmp(viewHoler.imageView as ImageView, item.thumbnailPath, item.imagePath, callback)
        }
        viewHoler.toggleButton!!.tag = position
        viewHoler.choosetoggle!!.tag = position
        viewHoler.toggleButton!!.setOnClickListener(ToggleClickListener(viewHoler.choosetoggle as Button))
        if (SelectedDataList.contains(dataList!![position])) {
            viewHoler.toggleButton!!.isChecked = true
            viewHoler.choosetoggle!!.visibility = View.VISIBLE
        } else {
            viewHoler.toggleButton!!.isChecked = false
            viewHoler.choosetoggle!!.visibility = View.GONE
        }
        return convertView
    }

    fun dipToPx(dip: Int): Int {
        return (dip * dm.density + 0.5f).toInt()
    }

    private inner class ToggleClickListener(internal var chooseBt: Button) : View.OnClickListener {
        override fun onClick(v: View) {
            if (v is ToggleButton) {
                val position = v.tag as Int
                if (dataList != null && mOnItemClickListener != null && position < dataList.size) {
                    mOnItemClickListener!!.onItemClick(v, position, v.isChecked, chooseBt)
                }

            }
        }
    }

    private var mOnItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(l: (ToggleButton, Int, Boolean, Button) -> Unit) {
        mOnItemClickListener = l as OnItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: ToggleButton, position: Int, isChecked: Boolean, chooseBt: Button)
    }

}
