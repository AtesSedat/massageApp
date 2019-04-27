package com.sedatates.massageapp

class UserClass {

    var user_name:String?=null
    var user_phone:String?=null
    var user_id:String?=null
    var user_photo:String?=null
    var user_category:String?=null


    constructor(user_name: String?, user_phone: String?, user_id: String?, user_photo: String?, user_category:String?) {
        this.user_name = user_name
        this.user_phone = user_phone
        this.user_id = user_id
        this.user_photo = user_photo
        this.user_category=user_category
    }
    constructor(){}
}