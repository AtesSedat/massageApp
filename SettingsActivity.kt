package com.sedatates.massageapp


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var user = FirebaseAuth.getInstance().currentUser

        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        readDataFromDatabase()



       // tvSettingsUserId.text = FirebaseAuth.getInstance().currentUser?.uid
        tvSettingsEmailAddress.text = FirebaseAuth.getInstance().currentUser?.email
        //tvSettingsUserName.text = FirebaseAuth.getInstance().currentUser?.displayName

        cardViewPassword.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(user?.email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Şifre yenileme maili gönderildi", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()

                    }
                }
        }
        cardViewEmail.setOnClickListener { }
        cardViewUserName.setOnClickListener {
           var createUserName=UserNameFragment()
            createUserName.show(supportFragmentManager,"username")

        }

        btnDelete.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser

            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Hesabınız Silindi", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }



}

    private fun readDataFromDatabase() {

        var userData=FirebaseDatabase.getInstance().reference
        var kullanici=FirebaseAuth.getInstance().currentUser

        var request=userData.child("user")
            .orderByKey()
            .equalTo(kullanici?.uid)

        request.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (singleSnapshot in p0.children){
                    var readDataSnapshot=singleSnapshot.getValue(UserClass::class.java)

                    tvSettingsUserId.text = readDataSnapshot?.user_id
                    tvSettingsPhone.text=readDataSnapshot?.user_phone
                    tvSettingsUserName.text = readDataSnapshot?.user_name

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        tvSettingsUserName.text = FirebaseAuth.getInstance().currentUser?.displayName
    }


}
