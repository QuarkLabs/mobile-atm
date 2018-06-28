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
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import retrofit2.http.Url
import java.net.URL
import java.util.*


class AccountFragment : Fragment() {
    private val TAG:String="AccountFragment"
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    //Firebase
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    // Points to 'profiles'
    var profilesRef:StorageReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.getReference()
        profilesRef= storageReference!!.child("profiles")

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

            var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


            var fileName: String? = user?.uid
            var profilePicture = profilesRef!!.child(fileName!!)

            Log.d(TAG, "*********************" + profilePicture.downloadUrl.toString())


            //var url: URL = URL("https://firebasestorage.googleapis.com/v0/b/mobile-atm-10742.appspot.com/o/profiles%2FMThDUb24tsgDIiOqy3rPvYITj9l2?alt=media&token=db655229-7f3a-41af-af46-0b4427d45c54")
            var options:RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_logo)
                .priority(Priority.HIGH)

            Glide.with(this /* context */)
                    .asBitmap()
                    .load(profilePicture)
                    .apply(options)
                    .into(fragmentAccountImage)
        }

        fragmentAccountSaveButton.setOnClickListener {
            if (Session.currentUser != null) {
                Session.currentUser!!.firstName = fragmentAccountFirstNameEditText.text.toString()
                Session.currentUser!!.lastName = fragmentAccountLastNameEditText.text.toString()
                Session.currentUser!!.account!!.balance = fragmentAccountAccountBalanceEditText.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimit = fragmentAccountSpendingLimitEditText.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimitEnable = fragmentAccountEnableSpendingLimitSwitch.isChecked


                uploadImage()

                Session.updateUser()
                Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show()
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
            //val progressDialog = ProgressDialog(MainActivity.context)
            //progressDialog.setTitle("Uploading...")
            //progressDialog.show()
            var user:FirebaseUser?  = FirebaseAuth.getInstance().currentUser
            val ref = profilesRef!!.child(user?.uid!!)
            ref.putFile(filePath!!)
                    .addOnSuccessListener {taskSnapshot ->
                        //progressDialog.dismiss()
                        //Session.currentUser!!.photoURL=taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                        Log.d(TAG, "*********************" +taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
                        //Session.updateUser()
                        //Toast.makeText(MainActivity.context, "Uploaded", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        //progressDialog.dismiss()
                        Toast.makeText(MainActivity.context, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                .totalByteCount
                        //progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
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
