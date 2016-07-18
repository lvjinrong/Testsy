package com.example.bean

import android.graphics.Bitmap

import com.example.myapplication.Bimp

import java.io.IOException
import java.io.Serializable

/**
 * Created by Administrator on 2016/6/15 0015.
 */
class ImageBean : Serializable {
    var imageId: String = ""
    var thumbnailPath: String = ""  //缩略图文件的路径
    var imagePath: String = ""      //图片路径
    var bitmap: Bitmap? = null
        get() {
            if (bitmap == null) {
                try {
                    bitmap = Bimp.revitionImageSize(imagePath)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return bitmap
        }         //位图文件
    var isSelected = false
}
