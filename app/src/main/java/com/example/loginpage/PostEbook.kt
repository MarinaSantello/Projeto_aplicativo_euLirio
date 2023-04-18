package com.example.loginpage

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.genre.CallGenreAPI
import com.example.loginpage.API.parentalRatings.CallParentalRatingsListAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.models.AnnouncementPost
import com.example.loginpage.models.Classificacao
import com.example.loginpage.models.Genero
import com.example.loginpage.models.Generos
import com.example.loginpage.resources.uploadFile
import com.example.loginpage.ui.components.GenerateGenresCards
import com.example.loginpage.ui.theme.*

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
                    PostDataEbook()
                }
            }
        }
    }
}
@SuppressLint("InvalidColorHexValue")
@Composable
fun PostDataEbook() {
    val context = LocalContext.current

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()

    var capaState by remember {
        mutableStateOf("")
    }

    var checkState by remember {
        mutableStateOf(false)
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
    var parentalRatings by remember {
        mutableStateOf(listOf<Classificacao>())
    }
    var idParentalRatings by remember {
        mutableStateOf(0)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    //Variaveis de estado para tratar os erros
    var checkTitle  by remember {
        mutableStateOf(false)
    }

    var checkPrice  by remember {
        mutableStateOf(false)
    }

    var checkClassification  by remember {
        mutableStateOf(false)
    }

    var checkVol  by remember {
        mutableStateOf(false)
    }

    var checkPages  by remember {
        mutableStateOf(false)
    }

    var checkGenres  by remember {
        mutableStateOf(false)
    }

    var checkPDF  by remember {
        mutableStateOf(false)
    }

    var checkEPUB  by remember {
        mutableStateOf(false)
    }

    var checkFoto by remember{
        mutableStateOf(false)
    }

    //Focus Requesters
    var titleFocusRequester = remember{
        FocusRequester()
    }

    var priceFocusRequester = remember{
        FocusRequester()
    }

    var volFocusRequester = remember{
        FocusRequester()
    }

    var pagesFocusRequester = remember{
        FocusRequester()
    }

    var capaStorage by remember {
        mutableStateOf("")
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

    val contentResolver: ContentResolver = context.contentResolver

    var pdfUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var pdfName by remember {
        mutableStateOf("Arquivo em PDF")
    }
    val selectPDF = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val cursor = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                val fileName = it.getString(nameIndex)

                pdfName = fileName
            }
        }
        pdfUri = uri

        Log.i("uri pdf", uri.toString())
    }

    var epubUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var epubName by remember {
        mutableStateOf("Arquivo em ePUB")
    }
    val selectEPUB = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val cursor = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                val fileName = it.getString(nameIndex)

                epubName = fileName
            }
        }
        epubUri = uri

        Log.i("uri epub", uri.toString())
    }

    var mobiUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var mobiName by remember {
        mutableStateOf("Arquivo em MOBI")
    }
    val selectMOBI = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        val cursor = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                val fileName = it.getString(nameIndex)

                mobiName = fileName
            }
        }
        mobiUri = uri

        Log.i("uri mobi", uri.toString())
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var expandedTopBar by remember {
        mutableStateOf(false)
    }

    var expandedTopBarState by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf(0)
    }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(colorResource(id = R.color.eulirio_beige_color_background))

    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.padding(start = 24.dp, end = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(199.dp)

                ){
                    Icon(Icons.Default.Close,
                        contentDescription = "icone para fechar",
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .clickable {
                                val intent = Intent(context, Home::class.java)
                                context.startActivity(intent)
                            })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.publicar),
                        Modifier.fillMaxWidth(),
                        fontFamily = Spartan,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }


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
                shape = RoundedCornerShape(8.dp), backgroundColor = Color(0x0D381871),
                border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_purple_text_color_border)),
                elevation = 0.dp
            ) {
                if (capaUri == null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Outlined.FileOpen, contentDescription = "",
                            modifier = Modifier.size(38.dp),
                            tint = if (!checkFoto) {
                                Color(0xff381871)
                            } else {
                                Color.Red
                            }
                        )
                        Text(
                            text = stringResource(id = R.string.adicionarimagem),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = SpartanRegular,
                            color = if (!checkFoto) {
                                Color(0xCC1E1E1E)
                            } else {
                                Color.Red
                            }
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        if (checkFoto) {
                            Icon(
                                Icons.Default.Error, contentDescription = "",
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(end = 10.dp, bottom = 6.dp),
                                tint = Color.Red
                            )
                        } else {
                            Icon(
                                Icons.Default.Error, contentDescription = "",
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(end = 10.dp, bottom = 6.dp)
                            )
                        }
                    }
                }

                else Image(
                    painter = rememberAsyncImagePainter(capaUri),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            //column titulo
            Column() {

                TextField(
                    modifier = Modifier
                        .focusRequester(titleFocusRequester),
                    value = titleState,
                    onValueChange = {
                        titleState = it
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.publicartitulo),
                            color = colorResource(id = R.color.eulirio_purple_text_color_border),
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
                        unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border)
                    ),
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.Error, contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = if(checkTitle){
                                Color.Red
                            }else{
                                colorResource(id = R.color.eulirio_purple_text_color_border)
                            }
                        )
                    }
                )

                TextField(
                    modifier = Modifier
                        .focusRequester(priceFocusRequester),
                    value = priceState,
                    onValueChange = {
                        // previnindo bug, caso o input fique vazio e, caso tenha algum valor, pegando o último valor do 'it' (último caracter digitado)
                        val lastChar = if (it.isEmpty()) it
                        else it.get(it.length - 1) // '.get': recupera um caracter de acordo com a posição do vetor que é passada no argumento da função
                        Log.i("console log", lastChar.toString()) // equivalente ao 'console.log'

                        // verifica se o último caracter é indesejado
                        val newValue =
                            if (lastChar == ' ' || lastChar == '-') it.dropLast(1)  // se sim, remove 1 caracter 'do final para o começo'
                            else it // se não, o valor digitado é mantido intacto

                        priceState = newValue // valor desejado, porque recebeu o valor do 'it'
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.precopub),
                            color = colorResource(id = R.color.eulirio_purple_text_color_border),
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        backgroundColor = Color.Transparent,
                        cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                        unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    ),
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.Error, contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = if(checkPrice){
                                Color.Red
                            }else{
                                colorResource(id = R.color.eulirio_purple_text_color_border)
                            }
                        )
                    }
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
                        color = colorResource(id = R.color.eulirio_purple_text_color_border),
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
                    unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border)
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            CallParentalRatingsListAPI.getClassificacoes {
                parentalRatings = it
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
            ){
                Text(
                    text = "Classificação indicativa",
                    fontFamily = Spartan,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.eulirio_purple_text_color_border)
                )

            }

            if (parentalRatings.isNotEmpty()) Box (Modifier.padding(top = 8.dp)) {
                Card(modifier = Modifier
                    .clickable { expanded = true }
                    .fillMaxWidth(),
                    backgroundColor = colorResource(id = R.color.eulirio_grey_background),
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)) {

                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = parentalRatings[selectedItem].classificacao,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(4.dp, 10.dp)
                        )
                        Icon(
                            Icons.Rounded.ExpandMore,
                            contentDescription = "Mostrar mais",
                            tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(.85f)
                ) {
                    parentalRatings.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = index
                                expanded = false

                                idParentalRatings = parentalRatings[index].idClassificacao!!
                            }
                        ) {
                            Text(text = item.classificacao)
                        }
                    }
                }
            }
            else Box (Modifier.padding(top = 8.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = colorResource(id = R.color.eulirio_grey_background),
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Livre",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(4.dp, 10.dp)
                        )
                        Icon(
                            Icons.Rounded.ExpandMore,
                            contentDescription = "Mostrar mais",
                            tint = colorResource(id = R.color.eulirio_purple_text_color_border)
                        )
                    }
                }
            }



            Card(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border)
            ) {}

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = volumeState,
                onValueChange = {
                    // previnindo bug, caso o input fique vazio e, caso tenha algum valor, pegando o último valor do 'it' (último caracter digitado)
                    val lastChar = if (it.isEmpty()) it
                    else it.get(it.length - 1) // '.get': recupera um caracter de acordo com a posição do vetor que é passada no argumento da função

                    // verifica se o último caracter é indesejado
                    val newValue =
                        if (lastChar == ' ' || lastChar == '-' || lastChar == "," || lastChar == ".") it.dropLast(1)  // se sim, remove 1 caracter 'do final para o começo'
                        else it // se não, o valor digitado é mantido intacto

                    volumeState = newValue // valor desejado, porque recebeu o valor do 'it'
                },
                label = {
                    Text(
                        text = "Volume",
                        color = colorResource(id = R.color.eulirio_purple_text_color_border),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    backgroundColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border)
                ),
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Error, contentDescription = "",
                        modifier = Modifier.size(15.dp),
                        tint = if(checkVol){
                            Color.Red
                        }else{
                            colorResource(id = R.color.eulirio_purple_text_color_border)
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(volFocusRequester)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = pagesState,
                onValueChange = {
                    // previnindo bug, caso o input fique vazio e, caso tenha algum valor, pegando o último valor do 'it' (último caracter digitado)
                    val lastChar = if (it.isEmpty()) it
                    else it.get(it.length - 1) // '.get': recupera um caracter de acordo com a posição do vetor que é passada no argumento da função

                    // verifica se o último caracter é indesejado
                    val newValue =
                        if (lastChar == ' ' || lastChar == '-' || lastChar == "," || lastChar == ".") it.dropLast(1)  // se sim, remove 1 caracter 'do final para o começo'
                        else it // se não, o valor digitado é mantido intacto

                    pagesState = newValue // valor desejado, porque recebeu o valor do 'it'
                },
                label = {
                    Text(
                        text = "Páginas",
                        color = colorResource(id = R.color.eulirio_purple_text_color_border),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    backgroundColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    focusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                    unfocusedIndicatorColor = colorResource(id = R.color.eulirio_purple_text_color_border)
                ),
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Error, contentDescription = "",
                        modifier = Modifier.size(15.dp),
                        tint = if(checkPages){
                            Color.Red
                        }else{
                            colorResource(id = R.color.eulirio_purple_text_color_border)
                        }
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(pagesFocusRequester)
            )

            Spacer(modifier = Modifier.height(12.dp))

            GenerateGenresCards() { state, idGenero ->
                if (state) generos += Genero(idGenero, "")

                else generos -= Genero(idGenero, "")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "E-book",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.eulirio_purple_text_color_border)
                )
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    //Card para importar PDF
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .width(112.dp)
                            .clickable {
                                selectPDF.launch("application/pdf")
                            },
                        border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_purple_text_color_border)),
                        shape = RoundedCornerShape(8.dp), backgroundColor = Color(0xD381871),
                        elevation = 0.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize(),

                            ) {
                            Icon(
                                Icons.Outlined.FileUpload, contentDescription = "",
                                modifier = Modifier.size(38.dp),
                                tint = Color(0xff381871)
                            )
                            Text(
                                text = pdfName,
                                textAlign = TextAlign.Center,
                                fontSize =  12.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = SpartanRegular,
                                color = Color(0xCC1E1E1E)

                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Bottom
                        ){
                            if(checkPDF){
                                Icon(
                                    Icons.Default.Error, contentDescription = "",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(end = 10.dp, bottom = 6.dp),
                                    tint = Color.Red
                                )
                            }else {
                                Icon(
                                    Icons.Default.Error, contentDescription = "",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(end = 10.dp, bottom = 6.dp)
                                )
                            }
                        }

                    }
                    //Card para importar ePUB
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .width(112.dp)
                            .clickable {
                                selectEPUB.launch("application/epub+zip")
                            },
                        border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_purple_text_color_border)),
                        shape = RoundedCornerShape(8.dp), backgroundColor = Color(0xD381871),
                        elevation = 0.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                Icons.Outlined.FileUpload, contentDescription = "",
                                modifier = Modifier.size(38.dp),
                                tint = Color(0xff381871)
                            )
                            Text(
                                text = epubName,
                                textAlign = TextAlign.Center,
                                fontSize =  12.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = SpartanRegular,
                                color = Color(0xCC1E1E1E)

                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Bottom
                        ){
                            if(checkEPUB){
                                Icon(
                                    Icons.Default.Error, contentDescription = "",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(end = 10.dp, bottom = 6.dp),
                                    tint = Color.Red
                                )
                            }else {
                                Icon(
                                    Icons.Default.Error, contentDescription = "",
                                    modifier = Modifier
                                        .size(25.dp)
                                        .padding(end = 10.dp, bottom = 6.dp)
                                )
                            }
                        }
                    }
                    //Card para importar MOBI
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .width(112.dp)
                            .clickable {
                                selectMOBI.launch("application/octet-stream")
                            },
                        border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_purple_text_color_border)),
                        shape = RoundedCornerShape(8.dp), backgroundColor = Color(0xD381871),
                        elevation = 0.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                Icons.Outlined.FileUpload, contentDescription = "",
                                modifier = Modifier.size(38.dp),
                                tint = Color(0xff381871)
                            )
                            Text(
                                text = mobiName,
                                textAlign = TextAlign.Center,
                                fontSize =  12.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = SpartanRegular,
                            )
                        }


                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {

                        //Verificação se o titulo está vazio
                        if (titleState.isEmpty()) {
                            checkTitle = true
                            titleFocusRequester.requestFocus()
                        } else {
                            checkTitle = false
                        }

                        //Verificação se o preço esta vazio
                        if (priceState.isEmpty()) {
                            checkPrice = true
                            priceFocusRequester.requestFocus()
                        } else {
                            checkPrice = false
                        }

                        //Verificação se o volume esta vazio
//                        if (volumeState.isEmpty()) {
//                            checkVol = true
//                            volFocusRequester.requestFocus()
//                        } else {
//                            checkVol = false
//                        }

                        //Verificação se o campo de páginas esta vazio
                        if (pagesState.isEmpty()) {
                            checkPages = true
                            pagesFocusRequester.requestFocus()
                        } else {
                            checkPages = false
                        }


                        //Verificação se o campo de inserção de PDF esta vazio
                        if (pdfUri == null) {
                            checkPDF = true
                        } else {
                            checkPDF = false
                        }

                        //Verificação se o campo de inserção de ePUB esta vazio
                        if (epubUri == null) {
                            checkEPUB = true
                        } else {
                            checkEPUB = false
                        }

                        //Verificação se o campo de inserção da capa do ebook esta vazio
                        if (capaUri == null) {
                            checkFoto = true
                        } else {
                            checkFoto = false
                        }

                        if (!checkTitle && !checkPrice && !checkClassification && !checkPages && !checkPDF && !checkEPUB && !checkFoto) {

                            var pdfStorage = ""
                            var epubStorage = ""
                            var mobiStorage = ""

                            if (mobiUri != null) mobiUri?.let {
                                uploadFile(
                                    it,
                                    "file",
                                    "$titleState-mobi",
                                    context
                                ) { storageMOBI ->
                                    mobiStorage = storageMOBI
                                }
                            }

                            capaUri?.let { uriCapa ->
                                uploadFile(uriCapa, "cover", titleState, context) { storageCapa ->

                                    pdfUri?.let { uriPDF ->
                                        uploadFile(
                                            uriPDF,
                                            "file",
                                            "$titleState-pdf",
                                            context
                                        ) { storagePDF ->
                                            pdfStorage = storagePDF

                                            epubUri?.let {uriEPUB ->
                                                uploadFile(
                                                    uriEPUB,
                                                    "file",
                                                    "$titleState-epub",
                                                    context
                                                ) { storageEPUB ->
                                                    epubStorage = storageEPUB

                                                    capaStorage = storageCapa

                                                    if (capaStorage != "") {
                                                        Log.i("teste post", "vsfdkk")

                                                        val announcement = AnnouncementPost(
                                                            titulo = titleState,
                                                            volume = volumeState.toInt() ?: 1,
                                                            capa = capaState,
                                                            sinopse = sinopseState,
                                                            qunatidadePaginas = pagesState.toInt(),
                                                            preco = priceState
                                                                .replace(',', '.')
                                                                .toFloat(),
                                                            idClassificacao = idParentalRatings,
                                                            idUsuario = users[0].idUser,
                                                            epub = epubStorage,
                                                            pdf = pdfStorage,
                                                            mobi = if (mobiStorage.isNotEmpty()) mobiStorage else null,
                                                            generos = generos,
                                                        )

                                                        CallAnnouncementAPI.postAnnouncement(announcement) {
                                                            if (it == 201) {
                                                                Log.i("retorno api", "parabens pelo minimo")
                                                            }

                                                            Log.i("retorno api", it.toString())
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
            ){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "PUBLICAR",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontFamily = SpartanBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview5() {
    LoginPageTheme {
        PostDataEbook()
    }
}