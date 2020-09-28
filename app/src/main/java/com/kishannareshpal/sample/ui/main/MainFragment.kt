package com.kishannareshpal.sample.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.kishannareshpal.bottomlistsheet.BottomListSheet
import com.kishannareshpal.bottomlistsheet.models.Item
import com.kishannareshpal.sample.R

class MainFragment : Fragment() {

    companion object {
        private const val TAG: String = "BottomSheetSampleLogs"

        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.bottomsheet.observe(viewLifecycleOwner) {

            val data = listOf(
                Item("Português"),
                Item("Ingles"),
                Item("Matematica"),
                Item("Geografia"),
                Item("Biologia"),
                Item("Desenho"),
                Item("Fisica"),
                Item("Quimica"),
                Item("Filosofia"),
                Item("Educacao Fisica"),
                Item("Mais uma disciplina"),
                Item("Pra lista ficar comprida"),
                Item("E aceitar fazer scroll"),
                Item("Disciplina com um nome bem comprido para ver se faz wrap"),
            )

            if (it == true) {
                // Show the bottom sheet
                BottomListSheet()
                    .title("Filtrar")
                    .items(data)
                    .onItemSelected { position ->
                        Log.d(TAG, "You have clicked item number $position : ${data[position]}")
                    }
                    .onConfirm { button, selectedItems ->
                        Log.d(TAG, "Applied ${selectedItems.size} filters!")
                    }
                    .preselectItems(listOf(1, 4))
                    .show(parentFragmentManager)


            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener {
            viewModel.showBottomSheet()
        }
    }

}