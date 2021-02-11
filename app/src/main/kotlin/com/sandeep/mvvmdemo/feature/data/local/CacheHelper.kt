package com.sandeep.mvvmdemo.feature.data.local

import android.content.Context
import java.io.*
import java.net.URLEncoder
import java.util.*
import java.util.concurrent.TimeUnit

class CacheHelper (val context: Context) {

    var cacheLifeMins = 30

    fun getCacheDirectory(): String {
        return context.cacheDir.path
    }

     fun save(key: String, value: String) {
        try {
            val fileName = URLEncoder.encode(key, "UTF-8")
            val cache = File(getCacheDirectory() + "/" + fileName + ".srl")
            val out: ObjectOutput = ObjectOutputStream(FileOutputStream(cache))
            out.writeUTF(value)
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun retrieve( key: String): String? {
        try {
            val fileName = URLEncoder.encode(key, "UTF-8")
            val cache = File(getCacheDirectory() + "/" + fileName + ".srl")
            if (cache.exists()) {
                val lastModDate = Date(cache.lastModified())
                val now = Date()
                val diffInMillisec = now.time - lastModDate.time
                val diffInMins = TimeUnit.MILLISECONDS.toMinutes(diffInMillisec)
                if (diffInMins > cacheLifeMins) {
                    cache.delete()
                    return ""
                }
                val `in` = ObjectInputStream(FileInputStream(cache))
                val value = `in`.readUTF()
                `in`.close()
                return value
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}