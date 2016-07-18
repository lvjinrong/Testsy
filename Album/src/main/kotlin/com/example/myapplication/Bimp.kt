package com.example.myapplication

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.ArrayList

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import com.example.bean.ImageBean

object Bimp {
    var max = 0

    var tempSelectBitmap = ArrayList<ImageBean>()   //选择的图片的临时列表

    @Throws(IOException::class)
    fun revitionImageSize(path: String): Bitmap {
        var `in` = BufferedInputStream(FileInputStream(
                File(path)))
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(`in`, null, options)
        `in`.close()
        var i = 0
        var bitmap: Bitmap? = null
        while (true) {
            if (options.outWidth shr i <= 1000 && options.outHeight shr i <= 1000) {
                `in` = BufferedInputStream(
                        FileInputStream(File(path)))
                options.inSampleSize = Math.pow(2.0, i.toDouble()).toInt()
                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeStream(`in`, null, options)
                break
            }
            i += 1
        }
        return bitmap!!
    }
}
