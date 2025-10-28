package com.saveetha.findamaid

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.io.*

open class VolleyMultipartRequest(
    method: Int,
    url: String,
    private val paramMap: Map<String, String>,      // Dynamic form fields
    private val dataMap: Map<String, DataPart>,      // Image or file fields
    private val listener: Response.Listener<NetworkResponse>,
    errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val boundary = "apiclient-${System.currentTimeMillis()}"
    private val mimeType = "multipart/form-data;boundary=$boundary"

    override fun getBodyContentType(): String = mimeType

    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)

        // Text Params
        paramMap.forEach { (key, value) ->
            dos.writeBytes("--$boundary\r\n")
            dos.writeBytes("Content-Disposition: form-data; name=\"$key\"\r\n\r\n")
            dos.writeBytes("$value\r\n")
        }

        // File Params
        dataMap.forEach { (key, dataPart) ->
            dos.writeBytes("--$boundary\r\n")
            dos.writeBytes("Content-Disposition: form-data; name=\"$key\"; filename=\"${dataPart.fileName}\"\r\n")
            dos.writeBytes("Content-Type: ${dataPart.type}\r\n\r\n")
            dos.write(dataPart.content)
            dos.writeBytes("\r\n")
        }

        // End Boundary
        dos.writeBytes("--$boundary--\r\n")
        return bos.toByteArray()
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
    }

    override fun deliverResponse(response: NetworkResponse) {
        listener.onResponse(response)
    }

    data class DataPart(
        val fileName: String,
        val content: ByteArray,
        val type: String = "application/octet-stream"
    )
}
