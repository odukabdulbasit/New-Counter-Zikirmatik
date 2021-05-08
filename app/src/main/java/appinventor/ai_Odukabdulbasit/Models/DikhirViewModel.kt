package appinventor.ai_odukabdulbasit.models

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class SaveState {
    SAVE,
    Cancel
}

enum class VisibleState{
    Yes,
    No,
    YesAndKeepOn
}

class DikhirViewModel(mcontextt: Context) : ViewModel() {


    lateinit var name: String

    val sharedPrefFile = "ZikirmatikZikirOkuma"
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor


    private var _dikhirListFromSP = MutableLiveData<MutableList<Dikhir>>()
    val dikhirListFromSP: LiveData<MutableList<Dikhir>>
        get() = _dikhirListFromSP


    private var _saveState = MutableLiveData<SaveState>()
    val saveState: LiveData<SaveState>
        get() = _saveState

    private var _dikhirName = MutableLiveData<String>()
    val dikhirName: LiveData<String>
        get() = _dikhirName

    private var _countNumber = MutableLiveData<Int>()
    val countNumber: LiveData<Int>
        get() = _countNumber

    private var _visibleState = MutableLiveData<VisibleState>()
    val visibleState : LiveData<VisibleState>
    get() = _visibleState

    init {
        _dikhirListFromSP.value = mutableListOf()
        sharedPreferences = mcontextt.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()


        for (i in sharedPreferences.all.entries) {
            _dikhirListFromSP.value?.add(Dikhir(i.key, i.value as Int))
        }

        Log.i("DikhieModelView", " Dikihir model view created")

        _saveState.value = SaveState.Cancel
    }



    fun sifirlaClicked() {
        _countNumber.value = 0
        editor.putInt(name, 0)
        editor.apply()
    }

    fun sayClicked() {
        _countNumber.value = _countNumber.value?.plus(1)
        editor.putInt(name, _countNumber.value!!)
        editor.apply()
    }

    fun silClicked() {
        _visibleState.value = VisibleState.Yes
    }

    fun evetClicked() {
        editor.remove(name)
        editor.commit()
        editor.apply()
        _dikhirListFromSP.value?.remove(Dikhir(name,_countNumber.value!!.toInt()))
        changeVisibilityAndSkip()
    }

    fun changeVisibilityAndSkip(){
        _visibleState.value = VisibleState.YesAndKeepOn
    }

    fun hayirClicked() {
        _visibleState.value = VisibleState.No
    }

    fun setNameFromListViewToTextView(mName: String) {
        name = mName
        _dikhirName.value = name
        _countNumber.value = sharedPreferences.getInt(name, 0)
    }

    fun addNewDikhirTodikhirList(dikhir: Dikhir) {

        Log.i("DikhirViewModel", "$dikhir")
        if (!dikhir.dikhirName.isNullOrEmpty()) {

            _dikhirListFromSP.value?.add(Dikhir(dikhir.dikhirName, 0))

            editor.putInt(dikhir.dikhirName, 0)
            editor.apply()

            _saveState.value = SaveState.SAVE
        }
    }

    fun onEventSaveComplete() {
        _saveState.value = SaveState.Cancel
    }

    fun onDeleteComplete(){
        _visibleState.value = VisibleState.No
    }
}

