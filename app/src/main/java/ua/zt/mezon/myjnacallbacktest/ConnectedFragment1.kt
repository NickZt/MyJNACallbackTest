package ua.zt.mezon.myjnacallbacktest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import ua.zt.mezon.myjnacallbacktest.databinding.FragmentConnectedFragment1Binding

class ConnectedFragment1 : Fragment() {
    private lateinit var viewModel: ConnectedFragment1ViewModel
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentConnectedFragment1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ConnectedFragment1ViewModel::class.java)
        binding = FragmentConnectedFragment1Binding.inflate(inflater, container, false)
        mainActivityViewModel.stringMessage.observe(viewLifecycleOwner) {
            it?.let {
                printText(it)
            }
        }
        val editText = binding.editText
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//not used
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mainActivityViewModel.sendDataToJna(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {
//not used
            }
        })

        return binding.root
    }

    fun printText(txt: String) {
        binding.sampleTextFromPresenter.text = txt
    }


    companion object {
        fun newInstance(): ConnectedFragment1 {
            return ConnectedFragment1()
        }
    }
}