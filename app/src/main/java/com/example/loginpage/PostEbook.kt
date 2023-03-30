package com.example.loginpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.Genero
import com.example.loginpage.resources.updateStorage
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.Spartan

class PostEbook : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    InputDataEbook(navController)
                }
            }
        }
    }
}

@Composable
fun InputDataEbook(navController: NavController) {
    val context = LocalContext.current

    var capaState by remember {
        mutableStateOf("")
    }
    var titleState by remember {
        mutableStateOf("")
    }
    var priceState by remember {
        mutableStateOf("")
    }
    var sinopseState by remember {
        mutableStateOf("")
    }
    var volumeState by remember {
        mutableStateOf("")
    }
    var pagesState by remember {
        mutableStateOf("")
    }
    var generos by remember {
        mutableStateOf(listOf<Genero>())
    }
    var pdfState by remember {
        mutableStateOf(listOf<Genero>())
    }
    var epubState by remember {
        mutableStateOf(listOf<Genero>())
    }
    var mobiState by remember {
        mutableStateOf(listOf<Genero>())
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var capaUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val selectImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        capaUri = uri

        Log.i("uri image", uri.toString())
    }

    var pdfUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val selectPDF = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pdfUri = uri

        Log.i("uri image", uri.toString())
    }

    var epubUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val selectEPUB = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        epubUri = uri

        Log.i("uri image", uri.toString())
    }

    var mobiUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val selectMOBI = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        mobiUri = uri

        Log.i("uri image", uri.toString())
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf(0)
    }
    Column() {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.065f),
            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.padding(start = 24.dp, top = 16.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "icone para fechar",
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable {
                            val intent = Intent(context, Home::class.java)
                            context.startActivity(intent)
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.publicar),
                    Modifier
                        .fillMaxWidth(),
                    fontFamily = Spartan,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
        }


        Row(
            Modifier.padding(20.dp, 12.dp)
        ) {

            Card(
                modifier = Modifier
                    .height(160.dp)
                    .width(120.dp)
                    .padding(start = 8.dp, top = 8.dp)
                    .clickable {
                        selectImage.launch("image/*")
                    },
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.LightGray
            ) {

                Icon(
                    Icons.Default.PermMedia, contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.adicionarimagem),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 26.dp),
                    fontSize = 16.sp
                )
            }


            //column titulo
            Column() {

                TextField(
                    value = titleState,
                    onValueChange = {
                        titleState = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.publicartitulo),
                            color = Color(0xFF1E1E1E),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        backgroundColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    )
                )

                TextField(
                    value = priceState,
                    onValueChange = {
                        priceState = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.precopub),
                            color = Color(0xFF1E1E1E),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.precificacaopub),
                            modifier = Modifier.padding(top = 2.dp),
                            color = Color(0xCC1E1E1E),
                            fontSize = 12.sp
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        backgroundColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    )
                )

            }

        }

        Spacer(modifier = Modifier.height(4.dp))

        Column(Modifier.padding(20.dp, 0.dp)) {
            TextField(
                value = sinopseState,
                onValueChange = {
                    sinopseState = it
                },
                modifier = Modifier
                    .heightIn(120.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.sinopsepub),
                        color = Color(0xFF1E1E1E),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                singleLine = false,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    backgroundColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Classificação indicativa",
                fontFamily = Spartan,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.eulirio_purple_text_color_border)
            )

            val items = listOf("Opção 1", "Opção 2")

            Box {
                Text(
                    text = items[selectedItem],
                    modifier = Modifier
                        .clickable(onClick = { expanded = true })
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = index
                                expanded = false
                            }
                        ) {
                            Text(text = item)
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview5() {
    LoginPageTheme {
        val navController = rememberNavController()
        InputDataEbook(navController)
    }
}