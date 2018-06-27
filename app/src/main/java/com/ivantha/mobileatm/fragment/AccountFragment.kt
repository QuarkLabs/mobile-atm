package com.ivantha.mobileatm.fragment


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session
import kotlinx.android.synthetic.main.fragment_account.*
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import com.ivantha.mobileatm.activity.MainActivity
import java.io.IOException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.OnProgressListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.UUID.randomUUID
import android.app.ProgressDialog
import android.graphics.BitmapFactory
import retrofit2.http.Url
import java.net.URL
import java.util.*


class AccountFragment : Fragment() {
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    //Firebase
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.getReference();

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Session.currentUser != null) {
            fragmentAccountFirstNameEditText.setText(Session.currentUser!!.firstName)
            fragmentAccountLastNameEditText.setText(Session.currentUser!!.lastName)
            fragmentAccountAccountBalanceEditText.setText(Session.currentUser!!.account!!.balance.toString())
            fragmentAccountSpendingLimitEditText.setText(Session.currentUser!!.account!!.spendingLimit.toString())
            fragmentAccountEnableSpendingLimitSwitch.isChecked = Session.currentUser!!.account!!.spendingLimitEnable

            /*var url: URL = URL(Session.currentUser?.photoURL)
            if(url!=null){

                var bmp:Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                fragmentAccountImage.setImageBitmap(bmp)
            }*/

        }

        fragmentAccountSaveButton.setOnClickListener {
            if (Session.currentUser != null) {
                Session.currentUser!!.firstName = fragmentAccountFirstNameEditText.text.toString()
                Session.currentUser!!.lastName = fragmentAccountLastNameEditText.text.toString()
                Session.currentUser!!.account!!.balance = fragmentAccountAccountBalanceEditText.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimit = fragmentAccountSpendingLimitEditText.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimitEnable = fragmentAccountEnableSpendingLimitSwitch.isChecked


                uploadImage()

                //Session.updateUser()
                //Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show()
            }
        }

        fragmentAccountChooseButton.setOnClickListener{
            chooseImage()
        }
    }

    companion object {

        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage() {

        if (filePath != null) {
            val progressDialog = ProgressDialog(MainActivity.context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference!!.child("profiles/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                    .addOnSuccessListener {taskSnapshot ->
                        progressDialog.dismiss()
                        Session.currentUser!!.photoURL=taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                        Session.updateUser()
                        Toast.makeText(MainActivity.context, "Uploaded", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(MainActivity.context, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                .totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(MainActivity.context!!.getContentResolver(), filePath)
                fragmentAccountImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
