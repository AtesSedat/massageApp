package com.sedatates.massageapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    lateinit var myAuthStateListener:FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initMyAuthStateListener()
       supportActionBar?.title = intent.getStringExtra("email")

        tv_user_email2.text=FirebaseAuth.getInstance().currentUser?.email

        readDataFromDatabase()


    }


    private fun initMyAuthStateListener() {

        myAuthStateListener= FirebaseAuth.AuthStateListener { task ->

            var user=task.currentUser

            if (user!=null) {


            }else{
                var intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                finish()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.sign_out_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.sign_out_menu->{

                var user =FirebaseAuth.getInstance().currentUser
                if(user!=null){
                    FirebaseAuth.getInstance().signOut()
                    var intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }

                return true
            }
            R.id.settings->{
                var intent2=Intent(this,SettingsActivity::class.java)
                startActivity(intent2)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun readDataFromDatabase() {

        var userData= FirebaseDatabase.getInstance().reference
        var kullanici=FirebaseAuth.getInstance().currentUser

        var request=userData.child("user")
            .orderByKey()
            .equalTo(kullanici?.uid)

        request.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (singleSnapshot in p0.children){
                    var readDataSnapshot=singleSnapshot.getValue(UserClass::class.java)


                    tv_user_phone2.text=readDataSnapshot?.user_phone
                    tv_user_name2.text = readDataSnapshot?.user_name

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        var user=FirebaseAuth.getInstance().currentUser
        if (user==null){
            var intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener { myAuthStateListener }

    }

    override fun onStop() {
        super.onStop()

            FirebaseAuth.getInstance().removeAuthStateListener { myAuthStateListener }

    }

}
