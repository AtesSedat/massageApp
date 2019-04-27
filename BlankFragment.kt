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
import kotlinx.android.synthetic.main.fragment_blank.view.*


class BlankFragment : DialogFragment() {

    lateinit var emailText: EditText
    lateinit var sendButton: Button
    lateinit var closeButton: Button
    lateinit var mContext: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view = inflater.inflate(R.layout.fragment_blank, container, false)

        sendButton = view.findViewById(R.id.btnFragmentSend)
        closeButton = view.findViewById(R.id.btnFragmentClose)
        emailText = view.findViewById(R.id.etFragmentEmail)
        mContext = activity!!

        closeButton.setOnClickListener {

            dialog.dismiss()
        }

        sendButton.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailText.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(mContext, "Şifre yenileme maili gönderildi", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(mContext, task.exception?.message, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }

        }

        return view
    }


}
