package com.example.loginpage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.API.genre.GenreCall
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.user.UserCall
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Tag
import com.example.loginpage.models.User
import com.example.loginpage.resources.authenticate
import com.example.loginpage.ui.components.GenreCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.google.firebase.auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPageThirdPart : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterPageThirdPartFun()
                }
            }
        }
    }
}

@Composable
fun RegisterPageThirdPartFun() {

    // obtendo a instancia do firebase
    val auth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    val intentThirdPart = (context as RegisterPageThirdPart).intent //Intent(context, RegisterPageSecondPart::class.java)

    val userName = intentThirdPart.getStringExtra("user")
    val email = intentThirdPart.getStringExtra("email")
    val senha = intentThirdPart.getStringExtra("senha")
    val nome = intentThirdPart.getStringExtra("nome")
    val dataNascimento = intentThirdPart.getStringExtra("data_nascimento")
    val tagsExtra = intentThirdPart.getIntegerArrayListExtra("tags")

    Log.i("teste email", email.toString())

    var generos by remember {
        mutableStateOf(listOf<Genero>())
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_eu_lirio),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .width(178.dp)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Card(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                        .height(440.dp),
                    shape = RoundedCornerShape(50.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White
                ) {
                    Box(){
                        Column(
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = "olá, $nome".uppercase(),
                                    modifier = Modifier
                                        .padding(top = 32.dp)
                                        .fillMaxWidth(),
                                    color = colorResource(id = R.color.eulirio_purple_text_color),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h2
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val descriptionP1 = stringResource(id = R.string.description_third_page_pt1)
                                val descriptionP2 = stringResource(id = R.string.description_third_page_pt2)
                                val descriptionP3 = stringResource(id = R.string.description_third_page_pt3)
                                val descriptionP4 = stringResource(id = R.string.description_third_page_pt4)
                                val descriptionP5 = stringResource(id = R.string.description_third_page_pt5)

                                Text(
                                    buildAnnotatedString {
                                        append("$descriptionP1 ")

                                        withStyle(style = SpanStyle(
                                            color = colorResource(id = R.color.eulirio_purple_text_color),
                                            fontWeight = FontWeight.W900
                                        )
                                        ) {
                                            append("$descriptionP2 ")
                                        }

                                        append("$descriptionP3 ")

                                        withStyle(style = SpanStyle(
                                            color = colorResource(id = R.color.eulirio_purple_text_color),
                                            fontWeight = FontWeight.W900
                                        )
                                        ) {
                                            append("$descriptionP4 ")
                                        }

                                        append(descriptionP5)
                                    },

                                    modifier = Modifier.padding(start = 34.dp, end = 34.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2,
                                )

                            }

//                            var genres by remember {
//                                mutableStateOf(listOf<Genre>())
//                            }
//
//                            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
//                            val genreCall = retrofit.create(GenreCall::class.java) // instância do objeto contact
//                            val callGetGenres01 = genreCall.getAll()
//
//                            // Excutar a chamada para o End-point
//                            callGetGenres01.enqueue(object :
//                                Callback<List<Genre>> { // enqueue: usado somente quando o objeto retorna um valor
//                                override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
//                                    genres = response.body()!!
//                                }
//
//                                override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
//                                }
//                            })

                            var genres by remember {
                                mutableStateOf(listOf<Genero>())
                            }

                            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
                            val genreCall = retrofit.create(GenreCall::class.java) // instância do objeto contact
                            val callGetGenres01 = genreCall.getAll()

                            // Excutar a chamada para o End-point
                            callGetGenres01.enqueue(object :
                                Callback<List<Genero>> { // enqueue: usado somente quando o objeto retorna um valor
                                override fun onResponse(call: Call<List<Genero>>, response: Response<List<Genero>>) {
                                    genres = response.body()!!

                                }

                                override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
                                    Log.i("teste gen", genres.toString())
                                }
                            })

//                            getGenres(){genres += it}

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),

                                // content padding
                                contentPadding = PaddingValues(
                                    start = 24.dp,
                                    end = 24.dp,
                                    top = 4.dp,
                                    bottom = 70.dp
                                )
                            ) {
                                items(
                                    items = genres
                                ) {
                                    GenreCard(it, colorResource(id = R.color.eulirio_purple), true){ state ->
                                        if (state) generos += Genero(it.idGenero)

                                        else generos -= Genero(it.idGenero)
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(Color(0x80FFFFFF))
                                .fillMaxWidth()
                                .height(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                //var teste = getCLickState()
                                //Log.i("teste-state", teste.toString())

                                var tags = listOf<Tag>()
                                for(i in 0 until tagsExtra!!.size)
                                    tags += listOf<Tag>(Tag(tagsExtra[i]))

                                accountCreate(context,
                                        email.toString(), senha.toString(),
                                        userName.toString(), dataNascimento.toString(), nome.toString(), tags, generos)

                            },
                                modifier = Modifier
                                    .width(200.dp),
                                shape = RoundedCornerShape(30.dp),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_purple))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.finish),
                                    color = colorResource(
                                        id = R.color.white
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}


fun accountCreate(context: Context,
                  email: String, password: String,
                  userName: String, dataNascimento: String, nome: String, tags: List<Tag>, generos: List<Genero>) {

    // obtendo uma instancia do firebase auth
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { it -> // retorna o resultado da autenticacao, quando completada com sucesso
            val user = User (
                userName = userName,
                dataNascimento = dataNascimento,
                nome = nome,
                email = email,
                uid = it.user!!.uid,
                tags = tags,
                generos = generos,
                anunciosActivate = null,
                anunciosDeactivate = null,
                shortStoriesActivate = null,
                shortStoriesDeactivate = null,
                seguindo = null
            )

            val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
            val userCall = retrofit.create(UserCall::class.java) // instância do objeto contact
            val callInsertUser = userCall.save(user)

            var responseValidate = 0

            // Excutar a chamada para o End-point
            callInsertUser.enqueue(object :
                Callback<String> { // enqueue: usado somente quando o objeto retorna um valor
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    responseValidate = response.code()

                    Log.i("erro fdp", responseValidate.toString())

                    if (responseValidate == 201){
                        authenticate(email, password, context)

                        Log.i("mensagem", "parabens pelo minimo")
                    }
                    Log.i("respon post", response.message().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i("respon post err", t.message.toString())
                }
            })
        }
        .addOnFailureListener { // retorna o resultado da autenticacao, quando ela falha
            try {
                throw it
            }
            catch (error: FirebaseAuthException) { // erro generico
                Toast.makeText(context, R.string.FirebaseAuthException, Toast.LENGTH_SHORT).show()
            }
        }
}


@Preview(showBackground = true)
@Composable
fun RegisterPageThirdPartPreview() {
    RegisterPageThirdPartPreview()
}