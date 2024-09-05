package br.senai.sp.jandira.rickandmortyapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.senai.sp.jandira.rickandmortyapi.screens.CharacterDetails
import br.senai.sp.jandira.rickandmortyapi.screens.listAllCharacters
import br.senai.sp.jandira.rickandmortyapi.ui.theme.RickAndMortyAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RickAndMortyAPITheme {
                listAllCharacters()
            }
        }
    }
}