package com.rickandmorty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rickandmorty.R
import com.rickandmorty.adapters.CharacterAdapter
import com.rickandmorty.helpers.RetrofitHelper
import com.rickandmorty.responses.GetAllCharacterResponse
import com.rickandmorty.servicies.RickAndMortyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.rickandmorty.entities.Character
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var vista: View
    lateinit var searchTextInputEditText: TextInputLayout
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_home, container, false)

        searchTextInputEditText= vista.findViewById(R.id.fragmenHomeTextEditSearchId)
        recyclerView= vista.findViewById(R.id.fragmentHomeRecyclerViewId)

        return vista
    }

    override fun onStart() {
        super.onStart()

        searchTextInputEditText.visibility= View.GONE
        val prefsSettings = PreferenceManager.getDefaultSharedPreferences(requireContext())

        if(prefsSettings.getBoolean("browserEnabled", true)){
            searchTextInputEditText.visibility= View.VISIBLE
        }

        CoroutineScope(Dispatchers.IO).launch {
            var call= RetrofitHelper.getRetrofit().create(RickAndMortyService:: class.java).getAllCharacters(ENDPOINT)
            var response: GetAllCharacterResponse? = call.body()
            if (response != null) {
                if(call.isSuccessful && response.results.isNotEmpty()){
                    withContext(Dispatchers.Main){
                        initRecyclerView(response.results)
                    }
                }
            }
        }

    }

    private fun initRecyclerView(characters: List<Character>){

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = CharacterAdapter(characters) { character -> onItemSelected(character) }
    }

    private fun onItemSelected(character: Character){
        var action = HomeFragmentDirections.actionHomeFragmentToCharacterFragment(character.id.toString())
        vista.findNavController().navigate(action)
    }

    companion object {
        const val ENDPOINT="character/?page=0"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}