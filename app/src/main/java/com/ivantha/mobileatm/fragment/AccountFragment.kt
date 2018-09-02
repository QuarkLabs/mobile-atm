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
import android.widget.SeekBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_account.*
import java.io.IOException


class AccountFragment : Fragment() {
    private val TAG: String = "AccountFragment"
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71

    // SeekBar Range
    var MIN = 0
    var MAX = 100
    var STEP = 10

    // Firebase
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    // Points to 'profiles'
    var profilesRef: StorageReference? = null

    //Session
    var currentUser: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentUser = this.getArguments()!!.getSerializable("currentUser") as User?

        profilesRef = FirebaseStorage.getInstance().getReference("/profiles")


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set max to seekbar
        fragmentAccountSpendingLimitSeekBar!!.max = (MAX - MIN) / STEP
        fragmentAccountSpendingLimitSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val progress_custom = MIN + (i * STEP)
                fragmentAccountSpendingLimitEditText!!.text = progress_custom.toString()
                currentUser!!.account!!.spendingLimit = progress_custom.toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
        if (currentUser != null) {
            fragmentAccountFirstNameEditText.setText(currentUser!!.firstName)
            fragmentAccountLastNameEditText.setText(currentUser!!.lastName)
            fragmentAccountAccountBalanceEditText.setText(currentUser!!.account!!.balance.toString())
            fragmentAccountSpendingLimitSeekBar.setProgress(currentUser!!.account!!.spendingLimit.toInt())
            fragmentAccountSpendingLimitTextView.setText(currentUser!!.account!!.spendingLimit.toInt().toString())
            fragmentAccountEnableSpendingLimitSwitch.isChecked = currentUser!!.account!!.spendingLimitEnable

            var profilePicture = profilesRef!!.child(FirebaseAuth.getInstance().currentUser!!.uid)

            profilePicture.downloadUrl.addOnSuccessListener { uri ->
                Picasso.with(context).load(uri.toString()).fit().centerCrop().into(fragmentAccountImage);
            }.addOnFailureListener {
                // TODO : Handle error
            }

        }

        fragmentAccountSaveButton.setOnClickListener {
            if (currentUser != null) {
                currentUser!!.firstName = fragmentAccountFirstNameEditText.text.toString()
                currentUser!!.lastName = fragmentAccountLastNameEditText.text.toString()
                currentUser!!.account!!.balance = fragmentAccountAccountBalanceEditText.text.toString().toDouble()
                //Session.currentUser!!.account!!.spendingLimit = fragmentAccountSpendingLimitEditText.text.toString().toDouble()
                currentUser!!.account!!.spendingLimitEnable = fragmentAccountEnableSpendingLimitSwitch.isChecked


                uploadImage()

                updateUser()
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
                        Toast.makeText(activity, "Failed " + e.message, Toast.LENGTH_SHORT).show()
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
                val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, filePath)
                fragmentAccountImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun updateUser(): Unit {
        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(currentUser)
    }
}
