package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.genre.GenreCall
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.user.UserCall
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.*
import com.example.loginpage.resources.updateStorage
import com.example.loginpage.resources.uploadFile
import com.example.loginpage.ui.components.NewGenreCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.Montserrat2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class UpdateActivity : ComponentActivity() {

    //lateinit var progressBar: ProgressBar
    lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UpdatePage()
                }
            }
        }
    }
}

@Composable
fun UpdatePage() {
    val context = LocalContext.current

    var photoState by remember {
        mutableStateOf("")
    }
    var nameState by remember {
        mutableStateOf("")
    }
    var nameStateRequired by remember {
        mutableStateOf(false)
    }
    var nameFocusRequester = remember {
        FocusRequester()
    }

    var originalUserName = ""
    var userNameState by remember {
        mutableStateOf("")
    }
    var userNameStateRequired by remember {
        mutableStateOf(false)
    }
    var userNameFocusRequester = remember {
        FocusRequester()
    }

    var duplicatedUserName by remember {
        mutableStateOf(false)
    }

    var biographyState by remember {
        mutableStateOf("")
    }


    var writerCheckState by remember {
        mutableStateOf(false)
    }
    var readerCheckState by remember {
        mutableStateOf(false)
    }

    var tagsValidate by remember {
        mutableStateOf(false)
    }

//    var tags by remember {
//        mutableStateOf(listOf<Tag>())
//    }
    var generos by remember {
        mutableStateOf(listOf<Genero>())
    }

    var generosValidate by remember {
        mutableStateOf(false)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var iconUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var invalidUsername by remember {
        mutableStateOf(false)
    }


    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)


    var colorValidateNameTextField = Color.Black
    var colorValidateUserTextField = Color.Black
    var colorValidateCheckBox = colorResource(id = R.color.eulirio_black)
    var colorValidateCheckedGenres = Color.Transparent

    CallAPI.getUser(userID) {
        photoState = it.foto
        nameState = it.nome
        userNameState = it.userName
        originalUserName = it.userName
        biographyState = it.biografia
    }


    val selectImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        iconUri = uri

        updateStorage(photoState)
        Log.i("uri image", uri.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_icone_eulirio),
                contentDescription = "",
                modifier = Modifier
                    .height(52.dp)
                    .padding(4.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                //.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row( // Row para conter a foto, o nome e o username do usuário
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp)
                                .clickable {
                                    selectImage.launch("image/*")
                                },
                            shape = RoundedCornerShape(40.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(iconUri ?: photoState),
                                contentScale = ContentScale.Crop,
                                contentDescription = "sua foto de perfil"
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            TextField(
                                value = nameState,
                                onValueChange = {
                                    nameState = it
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.name),
                                        color = Color(0xFF1E1E1E),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                },
                                singleLine = false,
                                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color(0xFF1E1E1E),
                                    backgroundColor = Color(0x66F2F2F2),
                                    cursorColor = Color(0xFF1E1E1E),
                                    focusedIndicatorColor = colorValidateNameTextField,
                                    unfocusedIndicatorColor = colorValidateNameTextField
                                ),
                                modifier = Modifier
                                    .focusRequester(nameFocusRequester),

                                isError = nameStateRequired

                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Column(
                                Modifier
                                    .background(
                                        colorResource(id = R.color.eulirio_light_yellow_background),
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp
                                        )
                                    )
                            ) {

                                TextField(
                                    value = userNameState,
                                    modifier = Modifier
                                        .focusRequester(userNameFocusRequester),
                                    onValueChange = {
                                        userNameState = it
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(id = R.string.user_name),
                                            color = Color(0xFF1E1E1E),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    },
                                    singleLine = false,
                                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color(0xFF1E1E1E),
                                        backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                                        cursorColor = Color(0xFF1E1E1E),
                                        focusedIndicatorColor = colorValidateUserTextField,
                                        unfocusedIndicatorColor = colorValidateUserTextField,

                                        )
                                )

                                if (invalidUsername) Text(
                                    modifier = Modifier
                                        .padding(start = 16.dp, top = 5.dp),
                                    text = stringResource(id = R.string.erro_message_invalid_user),
                                    color = Color(0xFFB00020),
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        Modifier
                            .background(
                                colorResource(id = R.color.eulirio_light_yellow_background),
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp
                                )
                            )
                            .drawWithContent {
                                drawContent()
                                clipRect { // Not needed if you do not care about painting half stroke outside
                                    val strokeWidth = Stroke.DefaultMiter
                                    val y = size.height // - strokeWidth
                                    // if the whole line should be inside component
                                    drawLine(
                                        brush = SolidColor(Color.Black),
                                        strokeWidth = strokeWidth,
                                        cap = StrokeCap.Square,
                                        start = Offset.Zero.copy(y = y),
                                        end = Offset(x = size.width, y = y)
                                    )
                                }
                            }
                    ) {
                        TextField(
                            value = biographyState,
                            modifier = Modifier
                                .heightIn(160.dp)
                                .fillMaxWidth(),
                            onValueChange = {
                                biographyState = it
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.biography),
                                    color = Color(0xFF1E1E1E),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            },
                            singleLine = false,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color(0xFF1E1E1E),
                                backgroundColor = colorResource(id = R.color.eulirio_light_yellow_background),
                                cursorColor = Color(0xFF1E1E1E),
                                focusedIndicatorColor = colorValidateUserTextField,
                                unfocusedIndicatorColor = colorValidateUserTextField
                            ),

                            isError = userNameStateRequired
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        Modifier
                            .background(
                                colorResource(id = R.color.eulirio_light_yellow_background),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp, top = 8.dp),
                            text = "Você é...",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .height(30.dp)
                        ) {
                            Row(
                                modifier = Modifier.width(80.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Spacer(modifier = Modifier.width(20.dp))
                                Checkbox(
                                    checked = writerCheckState,
                                    onCheckedChange = {
                                        writerCheckState = !writerCheckState
                                    },
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            colorValidateCheckBox,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .width(16.dp)
                                        .height(16.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = colorResource(id = R.color.eulirio_black),
                                        uncheckedColor = Color.Transparent
                                    ),

                                    )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = stringResource(id = R.string.tag_1_name).uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Montserrat2,
                                    color = colorResource(id = R.color.eulirio_black)
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Row(
                                modifier = Modifier.width(80.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = readerCheckState,
                                    onCheckedChange = {
                                        readerCheckState = !readerCheckState
                                    },
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            colorValidateCheckBox,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .width(16.dp)
                                        .height(16.dp),
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = colorResource(id = R.color.eulirio_black),
                                        uncheckedColor = Color.Transparent
                                    ),
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = stringResource(id = R.string.tag_2_name).uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Montserrat2,
                                    color = colorResource(id = R.color.eulirio_black)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.eulirio_light_yellow_background),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(1.dp, colorValidateCheckedGenres)
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Seus gêneros literários são:",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {


                            var genres by remember {
                                mutableStateOf(listOf<Genero>())
                            }

                            val retrofit =
                                RetrofitApi.getRetrofit() // pegar a instância do retrofit
                            val genreCall =
                                retrofit.create(GenreCall::class.java) // instância do objeto contact
                            val callGetGenres01 = genreCall.getAll()

                            // Excutar a chamada para o End-point
                            callGetGenres01.enqueue(object :
                                Callback<List<Genero>> { // enqueue: usado somente quando o objeto retorna um valor
                                override fun onResponse(
                                    call: Call<List<Genero>>,
                                    response: Response<List<Genero>>
                                ) {
                                    genres = response.body()!!

                                    Log.i("teste gen", genres.toString())
                                }

                                override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
                                }
                            })

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),

                                // content padding
                                contentPadding = PaddingValues(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 4.dp,
                                    bottom = 8.dp
                                )
                            ) {
                                items(
                                    items = genres
                                ) {
                                    NewGenreCard(it) { state ->
                                        if (state) generos += Genero(it.idGenero)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End
                    ) {

                        Button(
                            onClick = {

                                CallAPI.verifyUsername(userNameState) {
                                    duplicatedUserName = it
                                }

                                if (nameState.isEmpty()) {
                                    nameStateRequired = true;
                                    colorValidateNameTextField = Color.Red
                                    Toast.makeText(
                                        context,
                                        "O campo do seu nome precisa ser preenchido",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    nameFocusRequester.requestFocus()
                                } else{
                                    nameStateRequired = false
                                }

                                if (userNameState.isEmpty()) {
                                    userNameStateRequired = true;
                                    colorValidateUserTextField = Color.Red
                                    Toast.makeText(
                                        context,
                                        "O campo do seu nome de usuário precisa ser preenchido",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    userNameFocusRequester.requestFocus()
                                } else{
                                    userNameStateRequired = false
                                }

                                Log.i("testegenero", "${writerCheckState}")
                                Log.i("testegenero", "${readerCheckState}")
                                if (!writerCheckState) {
                                    tagsValidate = true;
                                    colorValidateCheckBox = Color.Red
                                    Toast.makeText(
                                        context,
                                        "O campo de sua tag precisa ser preenchido",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else{
                                    tagsValidate = false
                                }


                                Log.i("testegenero", "${generos}")
                                if (generos.isEmpty()) {
                                    generosValidate = true;
                                    colorValidateCheckedGenres = Color.Red
                                    Toast.makeText(
                                        context,
                                        "O campo para seus gêneros literários não pode estar vazio",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }else{
                                    generosValidate = false
                                    colorValidateCheckedGenres = Color.Black
                                }

                                if (userNameState != originalUserName) {
                                    if (duplicatedUserName) {
                                        userNameFocusRequester.requestFocus()
                                        colorValidateUserTextField = Color.Red
                                        Toast.makeText(
                                            context,
                                            "Este nome de usuário já existe. Por favor utilize um único.",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }


                                if (!userNameStateRequired && !nameStateRequired && !tagsValidate && !generosValidate && !duplicatedUserName) {

//                                    iconUri?.let {
//                                        uploadFile(it, "profile", userNameState, context) { url ->
//                                            Log.i("foto url", url)
//
//                                            var tag1: Int? = null
//                                            var tag2: Int? = null
//
//                                            if (writerCheckState)
//                                                tag1 = 1
//                                            if (readerCheckState)
//                                                tag2 = 2
//
//                                            val user = UserUpdate(
//                                                foto = url,
//                                                nome = nameState,
//                                                userName = userNameState,
//                                                biografia = biographyState,
//                                                tag01 = tag1,
//                                                tag02 = tag2,
//                                                generos = generos
//                                            )
//
//
//                                            val retrofit =
//                                                RetrofitApi.getRetrofit() // pegar a instância do retrofit
//                                            val userCall =
//                                                retrofit.create(UserCall::class.java) // instância do objeto contact
//                                            val callInsertUser =
//                                                userCall.update(userID.idUser, user)
//
//                                            var responseValidate = 0
//
//
//                                            // Excutar a chamada para o End-point
//                                            callInsertUser.enqueue(object :
//                                                Callback<String> { // enqueue: usado somente quando o objeto retorna um valor
//                                                override fun onResponse(
//                                                    call: Call<String>,
//                                                    response: Response<String>
//                                                ) {
//                                                    responseValidate = response.code()
//
//                                                    Log.i("erro fdp", responseValidate.toString())
//
//                                                    if (responseValidate == 200) {
//                                                        val intent =
//                                                            Intent(context, Home::class.java)
//                                                        context.startActivity(intent)
//                                                    }
//                                                    Log.i(
//                                                        "respon post",
//                                                        response.message().toString()
//                                                    )
//                                                }
//
//                                                override fun onFailure(
//                                                    call: Call<String>,
//                                                    t: Throwable
//                                                ) {
//                                                    Log.i("respon post err", t.message.toString())
//                                                }
//                                            })
//                                        }

//                                    }
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                            elevation = ButtonDefaults.elevation(0.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_save).uppercase(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }

                //column alert dialog
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {

                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .height(28.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_red)),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_light_yellow_background)),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_delete_account).uppercase(),
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.eulirio_red)
                        )
                    }


                    if (showDialog) {
                        AlertDialog(
                            shape = RoundedCornerShape(18.dp),
                            onDismissRequest = { showDialog = false },
                            title = {

                                Column(
                                    Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "lixo",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(top = 4.dp),
                                        tint = Color.Red
                                    )

                                    Text(
                                        "Deseja excluir a sua conta?",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                }
                            },
                            text = {
                                Text(
                                    "Esta ação é irreversível e resultará na exclusão completa de seus dados e publicações dentro da plataforma.",
                                    fontSize = 16.sp,

                                    )
                            },
                            confirmButton = {
                                Button(
                                    onClick = { showDialog = false },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                                    elevation = ButtonDefaults.elevation(0.dp)
                                ) {
                                    Text("Cancelar")

                                }
                                Button(
                                    onClick = {
                                        val retrofit =
                                            RetrofitApi.getRetrofit() // pegar a instância do retrofit
                                        val userCall =
                                            retrofit.create(UserCall::class.java) // instância do objeto contact
                                        val callDeleteUser = userCall.delete(userID.idUser)

                                        callDeleteUser.enqueue(object :
                                            Callback<String> {
                                            override fun onResponse(
                                                call: Call<String>,
                                                response: Response<String>
                                            ) {
                                                if (response.code() == 200) {
                                                    Toast.makeText(
                                                        context,
                                                        "Sua conta foi excluída com sucesso!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()

                                                    val auth = FirebaseAuth.getInstance()
                                                    auth.currentUser?.delete()

                                                    val intent = Intent(context, Home::class.java)
                                                    context.startActivity(intent)
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<String>,
                                                t: Throwable
                                            ) {

                                            }
                                        }
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                                    elevation = ButtonDefaults.elevation(0.dp)
                                ) {
                                    Text(text = "Excluir", color = Color.Red)

                                }
                            }

                        )

                    }
                }

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdatePreview() {
    LoginPageTheme {
        UpdatePage()
    }
}

