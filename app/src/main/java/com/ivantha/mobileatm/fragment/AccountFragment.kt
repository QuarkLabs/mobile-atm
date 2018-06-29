package com.ivantha.mobileatm.fragment


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.activity.MainActivity
import com.ivantha.mobileatm.common.Session
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_account.*
import java.io.IOException


class AccountFragment : Fragment() {
    private val TAG: String = "AccountFragment"
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    // Points to 'profiles'
    var profilesRef: StorageReference? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profilesRef = FirebaseStorage.getInstance().getReference("/profiles")

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

            var profilePicture = profilesRef!!.child(FirebaseAuth.getInstance().currentUser!!.uid)

            profilePicture.downloadUrl.addOnSuccessListener { uri ->
                Picasso.with(context).load(uri.toString()).fit().centerCrop().into(fragmentAccountImage);
            }.addOnFailureListener {
                // TODO : Handle error
            }

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

        fragmentAccountChooseButton.setOnClickListener {
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
            var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            val ref = profilesRef!!.child(user?.uid!!)
            ref.putFile(filePath!!)
                    .addOnSuccessListener { taskSnapshot ->
                        //progressDialog.dismiss()
                        //Session.currentUser!!.photoURL=taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                        Log.d(TAG, "*********************" + taskSnapshot.metadata!!.reference!!.downloadUrl.toString())
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
