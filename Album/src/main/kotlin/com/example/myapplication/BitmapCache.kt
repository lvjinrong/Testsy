package com.example.myapplication

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.ref.SoftReference
import java.util.HashMap

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView


class BitmapCache : Activity() {

    var h = Handler()
    val TAG = javaClass.simpleName
    private val imageCache = HashMap<String, SoftReference<Bitmap>>()


    fun put(path: String, bmp: Bitmap?) {
        if (!TextUtils.isEmpty(path) && bmp != null) {
            imageCache.put(path, SoftReference(bmp))
        }
    }

    fun displayBmp(iv: ImageView, thumbPath: String,
                   sourcePath: String, callback: ImageCallback?) {
        if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
            Log.e(TAG, "no paths pass in")
            return
        }

        val path: String
        val isThumbPath: Boolean
        if (!TextUtils.isEmpty(thumbPath)) {
            path = thumbPath
            isThumbPath = true
        } else if (!TextUtils.isEmpty(sourcePath)) {
            path = sourcePath
            isThumbPath = false
        } else {
            // iv.setImageBitmap(null);
            return
        }

        if (imageCache.containsKey(path)) {
            val reference = imageCache[path]
            val bmp = reference?.get()
            if (bmp != null) {
                callback?.imageLoad(iv, bmp, sourcePath)
                iv.setImageBitmap(bmp)
                Log.d(TAG, "hit cache")
                return
            }
        }
        iv.setImageBitmap(null)

        object : Thread() {
            internal var thumb: Bitmap? = null

            override fun run() {

                try {
                    if (isThumbPath) {
                        thumb = BitmapFactory.decodeFile(thumbPath)
                        if (thumb == null) {
                            thumb = revitionImageSize(sourcePath)
                        }
                    } else {
                        thumb = revitionImageSize(sourcePath)
                    }
                } catch (e: Exception) {

                }

                if (thumb == null) {
                    thumb = BitmapFactory.decodeResource(resources, R.drawable.plugin_camera_no_pictures)
                }
                Log.e(TAG, "-------thumb------" + thumb!!)
                put(path, thumb)

                if (callback != null) {
                    h.post { callback.imageLoad(iv, thumb as Bitmap, sourcePath) }
                }
            }
        }.start()

    }

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
            if (options.outWidth shr i <= 256 && options.outHeight shr i <= 256) {
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

    interface ImageCallback {
        fun imageLoad(imageView: ImageView, bitmap: Bitmap, vararg params: Any)
    }
}
