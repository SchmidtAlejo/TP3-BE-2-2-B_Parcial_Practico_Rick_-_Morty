package com.rickandmorty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.rickandmorty.R
import com.rickandmorty.helpers.RetrofitHelper
import com.rickandmorty.entities.Character
import com.rickandmorty.helpers.UserAplication.Companion.prefs
import com.rickandmorty.servicies.RickAndMortyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var vista: View
    lateinit var mainImage: ImageView
    lateinit var status: TextView
    lateinit var name: TextView
    lateinit var specie: TextView
    lateinit var origin: TextView
    lateinit var addFavorite: ImageView
    lateinit var characterId: String

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
        vista= inflater.inflate(R.layout.fragment_character, container, false)

        mainImage= vista.findViewById(R.id.fragmentCharacterImageViewMainId)
        status= vista.findViewById(R.id.fragmentCharacterTextViewStatusId)
        name= vista.findViewById(R.id.fragmentCharacterTextViewNameId)
        specie= vista.findViewById(R.id.fragmentCharacterTextViewSpecieId)
        origin= vista.findViewById(R.id.fragmentCharacterTextViewOriginId)
        addFavorite= vista.findViewById(R.id.fragmentCharacterImageViewAddFavoritesId)

        characterId= CharacterFragmentArgs.fromBundle(requireArguments()).characterId

        return vista
    }

    override fun onStart() {
        super.onStart()

        var favorites= prefs.getFavorites()
        val listFavorites= favorites?.split(",")
        val favoriteHelper= listFavorites?.find { character ->
            characterId == character
        }

        addFavorite.visibility= View.GONE
        val prefsSettings = PreferenceManager.getDefaultSharedPreferences(requireContext())

        if(prefsSettings.getBoolean("addFavoriteButtonEnabled", true)
            && favoriteHelper.isNullOrEmpty()){
            addFavorite.visibility= View.VISIBLE
        }



        CoroutineScope(Dispatchers.IO).launch {
            var call= RetrofitHelper.getRetrofit().create(RickAndMortyService:: class.java).getCharacterById("$ENDPOINT$characterId/")
            var response: Character? = call.body()
            if (response != null) {
                if(call.isSuccessful){
                    withContext(Dispatchers.Main){
                        Glide
                            .with(vista)
                            .load(response.image)
                            .centerCrop()
                            .into(mainImage)
                        status.text= "${getString(R.string.status)}: ${response.status}"
                        name.text= response.name
                        specie.text= "${getString(R.string.specie)}: ${response.species}"
                        origin.text= "${getString(R.string.origin)}: ${response.origin?.name}"
                    }
                }
            }
        }

        addFavorite.setOnClickListener{
            if (favorites != null) {
                if(favorites!!.isNotEmpty()){
                    favorites+=",$characterId"
                    addToFavorite()

                }
                else{
                    favorites=characterId
                    addToFavorite()
                }
            }
            if (favorites != null) {
                prefs.saveFavorites(favorites!!)
            }
        }
    }

    private fun addToFavorite(){
        Toast.makeText(requireActivity(), "Character added to favorites", Toast.LENGTH_SHORT).show()
        addFavorite.visibility= View.GONE
    }

    companion object {
        const val ENDPOINT= "character/"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}