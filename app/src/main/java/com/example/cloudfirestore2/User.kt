package com.example.cloudfirestore2

class User {
    var name:String? = null
    var pass:String? = null
    var id:String? = null

    constructor(){} // primary constructor not need arguments

    constructor(name:String?, pass:String?,id:String){ // secondary constructor
        this.name = name
        this.pass = pass
        this.id = id
    }
}