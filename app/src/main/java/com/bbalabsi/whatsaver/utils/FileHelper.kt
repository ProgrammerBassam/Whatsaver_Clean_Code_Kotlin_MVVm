package com.bbalabsi.whatsaver.utils

import android.webkit.MimeTypeMap
import java.io.File

fun getMimeType(path: File): String {
    var type = "image/jpeg" // Default Value
    val extension = path.extension
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)!!
      //  type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}
