package appinventor.ai_odukabdulbasit.zikirmatik

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import appinventor.ai_odukabdulbasit.models.DikhirViewModel
import appinventor.ai_odukabdulbasit.models.SampleViewModelFactory
import appinventor.ai_odukabdulbasit.models.VisibleState
import appinventor.ai_odukabdulbasit.zikirmatik.databinding.ActivityMainBinding
import appinventor.ai_odukabdulbasit.zikirmatik.databinding.FragmentReadDikhirBinding


class ReadDikhirFragment : Fragment() {

   lateinit var readDikhirViewModel : DikhirViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentReadDikhirBinding>(
            inflater, R.layout.fragment_read_dikhir, container, false
        )

        val factory = SampleViewModelFactory(this.activity!!.applicationContext)
        readDikhirViewModel = ViewModelProvider(requireActivity(), factory).get(DikhirViewModel ::class.java)
        binding.readDikhirViewModel = readDikhirViewModel
        binding.lifecycleOwner = this


        readDikhirViewModel.visibleState.observe(this as LifecycleOwner, Observer { state ->
            if (state == VisibleState.Yes){
                binding.UyariLayout.visibility = View.VISIBLE
            }else if (state == VisibleState.YesAndKeepOn){
                view?.findNavController()?.navigate(R.id.action_readDikhirFragment_to_dikhirListFragment)
                readDikhirViewModel.onDeleteComplete()
            }
            else{
                binding.UyariLayout.visibility = View.GONE
            }
        })

        return binding.root
    }

}