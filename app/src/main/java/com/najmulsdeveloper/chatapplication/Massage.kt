package com.najmulsdeveloper.chatapplication

class Massage {
    var massage: String? = null
    var senderId : String? = null


    constructor(){}

    constructor(massage : String?, SenderId: String?){
        this.massage = massage
        this.senderId = senderId

    }

}