package com.example.loginpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
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
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
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
import com.example.loginpage.API.user.CallAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Genre
import com.example.loginpage.models.Tag
import com.example.loginpage.models.User
import com.example.loginpage.resources.getGenres
import com.example.loginpage.ui.components.GenreCard
import com.example.loginpage.ui.components.NewGenreCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.google.firebase.storage.FirebaseStorage
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdatePage() {
    val context = LocalContext.current

    var photoState by remember {
        mutableStateOf("")
    }
    var nameState by remember {
        mutableStateOf("")
    }
    var userNameState by remember {
        mutableStateOf("")
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

    var tags by remember {
        mutableStateOf(listOf<Tag>())
    }
    var generos by remember {
        mutableStateOf(listOf<Genero>())
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var iconUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val selectImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        iconUri = uri
        Log.i("uri image", uri.toString())
    }

    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    CallAPI.getUser(userID){
        photoState = it.foto
        nameState = it.nome
        userNameState = it.userName
        biographyState = it.biografia
        //tags = it.tags
        //generos = it.generos
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
            modifier = Modifier.fillMaxSize(),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
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
                                painter = rememberAsyncImagePainter(iconUri ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                                contentScale = ContentScale.Crop,
                                contentDescription = "sua foto de perfil"
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column() {
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
                                    focusedIndicatorColor = Color(0xFF1E1E1E),
                                )
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
                                        focusedIndicatorColor = Color(0xFF1E1E1E),
                                    )
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
                                focusedIndicatorColor = Color(0xFF1E1E1E),
                            )
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
                                        writerCheckState = it
                                    },
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            Color.Black,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .width(16.dp)
                                        .height(16.dp)
                                    ,
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.Black,
                                        uncheckedColor = Color.Transparent
                                    ),
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = stringResource(id = R.string.tag_1_name).uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
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
                                        readerCheckState = it
                                    },
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            Color.Black,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .width(16.dp)
                                        .height(16.dp)
                                    ,
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.Black,
                                        uncheckedColor = Color.Transparent
                                    ),
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = stringResource(id = R.string.tag_2_name).uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
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
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp, top = 8.dp),
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
                                mutableStateOf(listOf<Genre>())
                            }
                            getGenres(){genres += it}

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),

                                // content padding
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    end = 12.dp,
                                    top = 5.dp,
                                    bottom = 8.dp
                                )
                            ) {
                                items(
                                    items = genres
                                ) {
                                    NewGenreCard(it){ state ->
                                        if (state) generos += Genero(it.id)
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
                                iconUri?.let {
                                    uploadFile(it, userNameState, context) {url ->
                                        photoState = url
                                    }
                                }

                                if (writerCheckState)
                                    tags += listOf<Tag>(Tag(1))
                                if (readerCheckState)
                                    tags += listOf<Tag>(Tag(2))

                                val user = User (
                                    foto = photoState,
                                    nome = nameState,
                                    userName = userNameState,
                                    biografia = biographyState,
                                    tags = tags,
                                    generos = generos
                                )
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
                                    onClick = { showDialog = false },
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

fun uploadFile(file: Uri, fileName: String, context: Context, uri: (String) -> Unit) {

    val datetime = LocalDateTime.now().toString()
    val discriminante = datetime.replace(' ', '-')

    //progressBar = findViewById(R.id.progressbar)
    //progressBar.visibility = View.VISIBLE

    val imageRef = FirebaseStorage
        .getInstance()
        .reference
        .child("profile/$fileName$discriminante")

    imageRef.putFile(file)
        .addOnSuccessListener { p0 ->
            //pd.dismiss()
            Toast.makeText(context, "File Uploaded", Toast.LENGTH_LONG).show()
            //progressBar.visibility = View.INVISIBLE

            imageRef
                .downloadUrl
                .addOnSuccessListener {
                    Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                    uri.invoke(it.toString())
                }
        }
        .addOnFailureListener { p0 ->
            Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
        }
        .addOnProgressListener { p0 ->
            var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
            //pd.setMessage("Uploaded ${progress.toInt()}%")
        }
}