package appinventor.ai_odukabdulbasit.models

import androidx.databinding.Bindable


data class Dikhir( var dikhirName : String, var rCount : Int = 0){

    fun getrCount() : String{
        return rCount.toString()
    }

}
