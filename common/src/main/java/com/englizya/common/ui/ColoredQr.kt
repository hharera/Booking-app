package com.englizya.common.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import com.google.zxing.BarcodeFormat
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class ColoredQr {

    //function to print colored qr code fot tickets
    fun generateQRCode(ticketQr: String, isActive: Boolean): Bitmap {
        val writer: Writer = QRCodeWriter()
        val mBitmap: Bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)

        val finalData: String = Uri.encode(ticketQr, "ISO-8859-1")
        try {
            val bm = writer.encode(finalData, BarcodeFormat.QR_CODE, 400, 400)
            for (i in 0..399) {
                for (j in 0..399) {
                    if (isActive) {
                        mBitmap.setPixel(
                            i,
                            j,
                            if (bm[i, j]) Color.argb(255, 0, 100, 0) else Color.WHITE
                        )
                    } else {
                        mBitmap.setPixel(
                            i,
                            j,
                            if (bm[i, j]) Color.argb(255, 153, 0, 0) else Color.WHITE
                        )
                    }
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return mBitmap
    }

}