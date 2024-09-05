package br.senai.sp.jandira.rickandmortyapi.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickandmortyapi.model.Character
import br.senai.sp.jandira.rickandmortyapi.model.Results
import br.senai.sp.jandira.rickandmortyapi.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun listAllCharacters(modifier: Modifier = Modifier) {

    var charactersList by remember {
        mutableStateOf(listOf<Character>())
    }

    val characterCall = RetrofitFactory()
        .getCharacterService()
        .getAllCharacters()

    characterCall.enqueue(
        object : Callback<Results> {
            override fun onResponse(p0: Call<Results>, response: Response<Results>) {
                charactersList = response.body()!!.results
            }

            override fun onFailure(p0: Call<Results>, p1: Throwable) {}

        }
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xffEDE9E6)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier
                    .padding(bottom = 32.dp),
                text = "Rick & Morty",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                items(charactersList) {
                    characterCard(it)
                }
            }

        }
    }
}

@Composable
fun characterCard(character: Character) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .height(100.dp)
            .clickable {
                Toast
                    .makeText(
                        context,
                        "CharID: ${character.id}",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
            ) {

                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = character.image,
                    contentDescription = "Imagem do Personagem"
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 4.dp, end = 12.dp, start = 12.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(
                    text = character.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(text = character.gender)

                Text(text = character.status)

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(
                        text = character.species,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun listAllCharactersPreview() {
    listAllCharacters()
}

@Preview()
@Composable
private fun characterCardPreview() {
    characterCard(Character())
}