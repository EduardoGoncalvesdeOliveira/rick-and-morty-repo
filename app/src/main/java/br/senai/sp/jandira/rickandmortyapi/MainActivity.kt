package br.senai.sp.jandira.rickandmortyapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.rickandmortyapi.model.Character
import br.senai.sp.jandira.rickandmortyapi.service.CharacterService
import br.senai.sp.jandira.rickandmortyapi.service.RetrofitFactory
import br.senai.sp.jandira.rickandmortyapi.ui.theme.RickAndMortyAPITheme
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RickAndMortyAPITheme {
                CharacterDetails()
            }
        }
    }
}

@Composable
fun CharacterDetails(modifier: Modifier = Modifier) {

    var id by remember {
        mutableStateOf("")
    }

    var character by remember {
        mutableStateOf(Character())
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                value = id,
                onValueChange = { id = it },
                trailingIcon = {
                    IconButton(onClick = {
                        val callCharacter = RetrofitFactory()
                            .getCharacterService()
                            .getCharacterById(id.toInt())

                        callCharacter.enqueue(object : Callback<Character> {

                            override fun onResponse(p0: Call<Character>, response1: Response<Character>) {
                                character= response1.body()!!
                            }

                            override fun onFailure(p0: Call<Character>, response1: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                }
            )
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Card(
                    modifier = Modifier
                        //.size(100.dp)
                        .height(224.dp)
                        .width(220.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = character.image,
                        contentDescription = "",
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

            }
            Column(
                modifier = Modifier
                    .width(220.dp),
            ) {


                Column(
                    modifier = Modifier,
                ) {

                    Text(text = "Nome: ${character.name}")
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )
                    Text(text = "Espécie: ${character.species}")
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                    )
                    Text(text = "Origem: ${character.origin.name}")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CharacterDetailsPreview() {
    CharacterDetails()
}