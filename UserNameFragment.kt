package com.sedatates.massageapp


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class UserNameFragment : DialogFragment() {

    lateinit var userNameText:EditText
    lateinit var closeButton: Button
    lateinit var createButton: Button
    lateinit var mContext: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_user_name, container, false)

        userNameText=view.findViewById(R.id.etUserName)
        closeButton=view.findViewById(R.id.btnUnfClose)
        createButton=view.findViewById(R.id.btnCreate)
        mContext= activity!!

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        createButton.setOnClickListener {
            var user=FirebaseAuth.getInstance().currentUser
           if (userNameText.text.isNotEmpty()){

                   var updateProfil=UserProfileChangeRequest.Builder()
                       .setDisplayName(userNameText.text.toString())
                       .build()

                   user?.updateProfile(updateProfil)?.addOnCompleteListener{ task ->
                       if (task.isSuccessful){

                           Toast.makeText(mContext, "Profil Güncellendi", Toast.LENGTH_SHORT).show()
                           dialog.dismiss()

                       }else{
                           Toast.makeText(mContext, task.exception?.message, Toast.LENGTH_SHORT).show()
                           dialog.dismiss()
                       }
                   }
               }else{
                   Toast.makeText(mContext, "Kullanıcı adı giriniz", Toast.LENGTH_SHORT).show()
               }
           }


        return view
    }



}
