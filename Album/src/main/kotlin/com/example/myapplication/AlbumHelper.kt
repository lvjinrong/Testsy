package com.example.myapplication

import java.util.ArrayList
import java.util.HashMap

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore.Audio.Albums
import android.provider.MediaStore.Images.Media
import android.provider.MediaStore.Images.Thumbnails
import android.util.Log

import com.example.bean.ImageBean
import com.example.bean.ImageBucket
import java.util.Map

class AlbumHelper private constructor() {
    internal val TAG = javaClass.simpleName
    internal var context: Context? = null
    internal var cr: ContentResolver = null!!

    internal var thumbnailList = HashMap<String, String>()

    internal var albumList: MutableList<HashMap<String, String>> = ArrayList()
    internal var bucketList = HashMap<String, ImageBucket>()

    fun init(context: Context) {
        if (this.context == null) {
            this.context = context
            cr = context.contentResolver
        }
    }

    private fun getThumbnail() {
        val projection = arrayOf(Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA)
        val cursor = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection,
                null, null, null)
        getThumbnailColumnData(cursor)
    }

    private fun getThumbnailColumnData(cur: Cursor) {
        if (cur.moveToFirst()) {
            var _id: Int
            var image_id: Int
            var image_path: String
            val _idColumn = cur.getColumnIndex(Thumbnails._ID)
            val image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID)
            val dataColumn = cur.getColumnIndex(Thumbnails.DATA)

            do {
                // Get the field values
                _id = cur.getInt(_idColumn)
                image_id = cur.getInt(image_idColumn)
                image_path = cur.getString(dataColumn)

                // Do something with the values.
                // Log.i(TAG, _id + " image_id:" + image_id + " path:"
                // + image_path + "---");
                // HashMap<String, String> hash = new HashMap<String, String>();
                // hash.put("image_id", image_id + "");
                // hash.put("path", image_path);
                // thumbnailList.add(hash);
                thumbnailList.put("" + image_id, image_path)
            } while (cur.moveToNext())
        }
    }

    internal fun getAlbum() {
        val projection = arrayOf(Albums._ID, Albums.ALBUM, Albums.ALBUM_ART, Albums.ALBUM_KEY, Albums.ARTIST, Albums.NUMBER_OF_SONGS)
        val cursor = cr.query(Albums.EXTERNAL_CONTENT_URI, projection, null,
                null, null)
        getAlbumColumnData(cursor)

    }

    private fun getAlbumColumnData(cur: Cursor) {
        if (cur.moveToFirst()) {
            var _id: Int
            var album: String
            var albumArt: String
            var albumKey: String
            var artist: String
            var numOfSongs: Int

            val _idColumn = cur.getColumnIndex(Albums._ID)
            val albumColumn = cur.getColumnIndex(Albums.ALBUM)
            val albumArtColumn = cur.getColumnIndex(Albums.ALBUM_ART)
            val albumKeyColumn = cur.getColumnIndex(Albums.ALBUM_KEY)
            val artistColumn = cur.getColumnIndex(Albums.ARTIST)
            val numOfSongsColumn = cur.getColumnIndex(Albums.NUMBER_OF_SONGS)

            do {
                // Get the field values
                _id = cur.getInt(_idColumn)
                album = cur.getString(albumColumn)
                albumArt = cur.getString(albumArtColumn)
                albumKey = cur.getString(albumKeyColumn)
                artist = cur.getString(artistColumn)
                numOfSongs = cur.getInt(numOfSongsColumn)

                // Do something with the values.
                Log.i(TAG,"" + _id + " album:" + album + " albumArt:" + albumArt
                        + "albumKey: " + albumKey + " artist: " + artist
                        + " numOfSongs: " + numOfSongs + "---")
                val hash = HashMap<String, String>()
                hash.put("_id","" + _id )
                hash.put("album", album)
                hash.put("albumArt", albumArt)
                hash.put("albumKey", albumKey)
                hash.put("artist", artist)
                hash.put("numOfSongs", "" + numOfSongs )
                albumList.add(hash)

            } while (cur.moveToNext())
        }
    }

    internal var hasBuildImagesBucketList = false

    internal fun buildImagesBucketList() {
        val startTime = System.currentTimeMillis()

        getThumbnail()

        val columns = arrayOf(Media._ID, Media.BUCKET_ID, Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE, Media.SIZE, Media.BUCKET_DISPLAY_NAME)
        val cur = cr.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
                null)
        if (cur!!.moveToFirst()) {
            val photoIDIndex = cur.getColumnIndexOrThrow(Media._ID)
            val photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA)
            val photoNameIndex = cur.getColumnIndexOrThrow(Media.DISPLAY_NAME)
            val photoTitleIndex = cur.getColumnIndexOrThrow(Media.TITLE)
            val photoSizeIndex = cur.getColumnIndexOrThrow(Media.SIZE)
            val bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME)
            val bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID)
            val picasaIdIndex = cur.getColumnIndexOrThrow(Media.PICASA_ID)
            val totalNum = cur.count

            do {
                val _id = cur.getString(photoIDIndex)
                val name = cur.getString(photoNameIndex)
                val path = cur.getString(photoPathIndex)
                val title = cur.getString(photoTitleIndex)
                val size = cur.getString(photoSizeIndex)
                val bucketName = cur.getString(bucketDisplayNameIndex)
                val bucketId = cur.getString(bucketIdIndex)
                val picasaId = cur.getString(picasaIdIndex)

                Log.i(TAG, "$_id, bucketId: $bucketId, picasaId: $picasaId name:$name path:$path title: $title size: $size bucket: $bucketName---")

                var bucket: ImageBucket? = bucketList[bucketId]
                if (bucket == null) {
                    bucket = ImageBucket()
                    bucketList.put(bucketId, bucket)
                    bucket.imageList = ArrayList<ImageBean>()
                    bucket.bucketName = bucketName
                }
                bucket.count++
                val imageItem = ImageBean()
                imageItem.imageId = _id
                imageItem.imagePath = path
                imageItem.thumbnailPath = thumbnailList[_id]!!
                bucket.imageList?.add(imageItem)

            } while (cur.moveToNext())
        }

        val itr = bucketList.entries.iterator()
        while (itr.hasNext()) {
            val entry = itr.next() as Map.Entry<String, ImageBucket>
            val bucket = entry.value
            Log.d(TAG, entry.key + ", " + bucket.bucketName + ", "
                    + bucket.count + " ---------- ")
            for (i in bucket.imageList!!.indices) {
                val image = bucket.imageList!!.get(i)
                Log.d(TAG, "----- " + image.imageId + ", " + image.imagePath
                        + ", " + image.thumbnailPath)
            }
        }
        hasBuildImagesBucketList = true
        val endTime = System.currentTimeMillis()
        Log.d(TAG, "use time: " + (endTime - startTime) + " ms")
    }


    fun getImagesBucketList(refresh: Boolean): List<ImageBucket> {
        if (refresh || !refresh && !hasBuildImagesBucketList) {
            buildImagesBucketList()
        }
        val tmpList = ArrayList<ImageBucket>()
        val itr = bucketList.entries.iterator()
        while (itr.hasNext()) {
            val entry = itr.next() as Map.Entry<String, ImageBucket>
            tmpList.add(entry.value)
        }
        return tmpList
    }

    internal fun getOriginalImagePath(image_id: String): String {
        var path: String? = null
        Log.i(TAG, "---(^o^)----" + image_id)
        val projection = arrayOf(Media._ID, Media.DATA)
        val cursor = cr.query(Media.EXTERNAL_CONTENT_URI, projection,
                Media._ID + "=" + image_id, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            path = cursor.getString(cursor.getColumnIndex(Media.DATA))

        }
        return path as String
    }

    companion object {

        private var instance: AlbumHelper? = null

        val helper: AlbumHelper
            get() {
                if (instance == null) {
                    instance = AlbumHelper()
                }
                return instance as AlbumHelper
            }
    }

}

