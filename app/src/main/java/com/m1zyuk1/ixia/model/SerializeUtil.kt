package com.m1zyuk1.ixia.model

import android.util.Base64

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


object SerializeUtil {
    fun toBase64(schedule: List<Post>): String {

        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null

        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(schedule)

            val bytes = byteArrayOutputStream.toByteArray()
            val base64 = Base64.encode(bytes, Base64.NO_WRAP)

            return String(base64)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return ""
    }

    fun toSchedules(base64: String): MutableList<Post> {

        var byteArrayInputStream: ByteArrayInputStream? = null
        var objectInputStream: ObjectInputStream? = null

        val bytes = Base64.decode(base64.toByteArray(), Base64.NO_WRAP)

        try {
            byteArrayInputStream = ByteArrayInputStream(bytes)
            objectInputStream = ObjectInputStream(byteArrayInputStream)
            return objectInputStream.readObject() as MutableList<Post>
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return mutableListOf()
    }
}
