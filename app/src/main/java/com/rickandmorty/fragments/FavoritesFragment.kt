package com.rickandmorty.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rickandmorty.R
import com.rickandmorty.adapters.CharacterAdapter
import com.rickandmorty.entities.Character
import com.rickandmorty.helpers.RetrofitHelper
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
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var vista: View
    lateinit var title: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var emptyFavorites: Button
    lateinit var notFound: TextView

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
        vista= inflater.inflate(R.layout.fragment_favorites, container, false)

        title= vista.findViewById(R.id.fragmentFavoritesTitleId)
        recyclerView= vista.findViewById(R.id.fragmentFavoritesRecyclerViewId)
        emptyFavorites= vista.findViewById(R.id.fragmentFavoritesEmptyFavoritesId)
        notFound= vista.findViewById(R.id.fragmentFavoritesNotFoundId)
        emptyFavorites.visibility= View.GONE
        notFound.visibility= View.GONE

        return vista
    }

    override fun onStart() {
        super.onStart()

        emptyFavorites.setOnClickListener {
            prefs.saveFavorites("")
            val action= FavoritesFragmentDirections.actionFavoritesFragmentSelf()
            vista.findNavController().navigate(action)
        }

        val favoritesString= prefs.getFavorites()
        if (favoritesString != null) {
            if (favoritesString.isNotEmpty()) {
                if(favoritesString.split(",").size>1){
                    CoroutineScope(Dispatchers.IO).launch {
                        var call= RetrofitHelper.getRetrofit().create(RickAndMortyService:: class.java).getMultipliesCharacter("$ENDPOINT$favoritesString/")
                        val response= call.body().toString()
                        if (call.isSuccessful){
                            if (response!=null){
                                val list=fromJson(response)
                                withContext(Dispatchers.Main){
                                    if (list != null) {
                                        initRecyclerView(list)
                                        title.text="Hi ${prefs.getUser()}, these are you favorites characters"
                                        emptyFavorites.visibility= View.VISIBLE
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    CoroutineScope(Dispatchers.IO).launch {
                        var call= RetrofitHelper.getRetrofit().create(RickAndMortyService:: class.java).getCharacterById("${CharacterFragment.ENDPOINT}$favoritesString/")
                        var response: Character? = call.body()
                        if (response != null) {
                            if(call.isSuccessful){
                                val list= ArrayList<Character>()
                                list.add(response)
                                withContext(Dispatchers.Main){
                                    title.text="Hi ${prefs.getUser()}, these are you favorites characters"
                                    initRecyclerView(list.toList())
                                    emptyFavorites.visibility= View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
            else{
                notFound.visibility= View.VISIBLE
            }
        }
    }

    private fun initRecyclerView(characters: List<Character>){

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = CharacterAdapter(characters) { character -> onItemSelected(character) }
    }

    private fun onItemSelected(character: Character){
        var action = FavoritesFragmentDirections.actionFavoritesFragmentToCharacterFragment(character.id.toString())
        vista.findNavController().navigate(action)
    }

    private fun fromJson(json: String): List<Character>? {
        val typeToken = object : TypeToken<List<Character>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    companion object {
        const val ENDPOINT= "character/"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoritesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}