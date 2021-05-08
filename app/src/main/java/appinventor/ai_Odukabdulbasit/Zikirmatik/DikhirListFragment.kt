package appinventor.ai_odukabdulbasit.zikirmatik


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import appinventor.ai_odukabdulbasit.models.Dikhir
import appinventor.ai_odukabdulbasit.models.DikhirViewModel
import appinventor.ai_odukabdulbasit.models.SampleViewModelFactory
import appinventor.ai_odukabdulbasit.models.SaveState
import appinventor.ai_odukabdulbasit.zikirmatik.databinding.FragmentDikhirListBinding

class DikhirListFragment : Fragment() {

    lateinit var dikhirsViewModel: DikhirViewModel

    private val dikhirData = Dikhir("")

    lateinit var arrayAdapter : rowAdapter

    var dikhirsArrayList : ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentDikhirListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dikhir_list, container, false
        )

        val factory = SampleViewModelFactory(this.activity!!.applicationContext)
        dikhirsViewModel = ViewModelProvider(requireActivity(), factory).get(DikhirViewModel::class.java)
        binding.dikhirViewModel = dikhirsViewModel
        binding.lifecycleOwner = this
        binding.dikhirData = dikhirData


        arrayAdapter = rowAdapter(requireContext(), dikhirsArrayList)
        binding.zikirList.adapter = arrayAdapter

        dikhirsViewModel.dikhirListFromSP.observe(this as LifecycleOwner, Observer { dikhirList ->

            dikhirsArrayList.clear()
            binding.editText.text.clear()

            for (i in dikhirsViewModel.dikhirListFromSP.value!!) {

                dikhirsArrayList.add(i.dikhirName)
                arrayAdapter.notifyDataSetChanged()
            }
        })

        dikhirsViewModel.saveState.observe(this as LifecycleOwner, Observer { statte ->
            if (statte == SaveState.SAVE) {

                dikhirsArrayList.clear()
                binding.editText.text.clear()
                hideKeyBoard()

                dikhirsViewModel.onEventSaveComplete()

                for (i in dikhirsViewModel.dikhirListFromSP.value!!) {

                    dikhirsArrayList.add(i.dikhirName)
                    arrayAdapter.notifyDataSetChanged()
                }
            }
        })



        binding.zikirList.setOnItemClickListener { parent, view, position, id ->

            view?.findNavController()?.navigate(R.id.action_dikhirListFragment_to_readDikhirFragment)
            dikhirsViewModel.setNameFromListViewToTextView(dikhirsArrayList[position])
        }
        return binding.root
    }

    fun hideKeyBoard(){
       /* val view = this.currentFocus
        if (view != null){
            val imm = (Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }*/
        val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
    }
}
