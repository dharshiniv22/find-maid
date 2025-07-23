package com.saveetha.findmaid.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    fun getPath(context: Context, uri: Uri): String? {
        val returnCursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        returnCursor?.moveToFirst()

        val fileName = if (nameIndex != -1) returnCursor?.getString(nameIndex) else "temp_file"
        returnCursor?.close()

        val file = File(context.cacheDir, fileName ?: "temp_file")

        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var read: Int

            while (inputStream?.read(buffer).also { read = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, read)
            }

            inputStream?.close()
            outputStream.close()
            return file.absolutePath

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
