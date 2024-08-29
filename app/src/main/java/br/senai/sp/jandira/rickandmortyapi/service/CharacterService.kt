package br.senai.sp.jandira.rickandmortyapi.service

import br.senai.sp.jandira.rickandmortyapi.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Call<Character>
}