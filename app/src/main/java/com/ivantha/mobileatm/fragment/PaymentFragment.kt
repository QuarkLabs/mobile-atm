package com.ivantha.mobileatm.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.model.Transaction
import com.ivantha.mobileatm.service.TransactionServices
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentFragmentStartButton.setOnClickListener {
            val transaction = Transaction()
            transaction.title = paymentFragmentTitleTextView.text.toString()
            transaction.description = paymentFragmentDescriptionTextView.text.toString()
            transaction.initiatorId = FirebaseAuth.getInstance().currentUser!!.uid
            transaction.initiatorName = FirebaseAuth.getInstance().currentUser!!.displayName

            if (paymentFragmentSendRadioButton.isChecked) {
                transaction.intention = Transaction.Intention.SEND
            } else {
                transaction.intention = Transaction.Intention.REQUEST
            }

            if (paymentFragmentAmountTextView.text.toString().equals("")) {
                transaction.amount = 0.0
            } else {
                transaction.amount = paymentFragmentAmountTextView.text.toString().toDouble()
            }

            val message = TransactionServices.transactionToJson(transaction)
            val multiFormatWriter = MultiFormatWriter()
            try {
                val bitMatrix: BitMatrix = multiFormatWriter.encode(message, BarcodeFormat.QR_CODE, 1000, 1000)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

                val imageView = ImageView(context)
                imageView.setImageBitmap(bitmap)

                val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                        .setMessage(message)
                        .setView(imageView)
                builder.create().show()
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
    }

    companion object {

        fun newInstance(): PaymentFragment {
            return PaymentFragment()
        }
    }
}
