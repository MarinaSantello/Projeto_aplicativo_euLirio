package com.example.loginpage

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
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
import androidx.compose.ui.draw.clip
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
import com.example.loginpage.API.parentalRatings.CallParentalRatingsListAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.AnnouncementGet
import com.example.loginpage.models.AnnouncementPost
import com.example.loginpage.models.Classificacao
import com.example.loginpage.models.Genero
import com.example.loginpage.resources.getName
import com.example.loginpage.resources.updateStorage
import com.example.loginpage.resources.uploadFile
import com.example.loginpage.ui.components.GenerateGenresCards
import com.example.loginpage.ui.theme.*

class EditEbook : ComponentActivity() {
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
                    EditDataEbook(0, navController)
                }
            }
        }
    }
}

@Composable
fun addDataAnnouncement(context: Context, announcementID: Int, title: (MutableState<String>) -> Unit) {

    val titulo = remember {
        mutableStateOf("")
    }
    val userIDRepository = UserIDrepository(context).getAll()

    CallAnnouncementAPI.getAnnouncement(announcementID, userIDRepository[0].idUser) {
        titulo.value = it.titulo

        title.invoke(titulo)
    }
}
@Composable
fun EditDataEbook(
    announcementID: Int,
    navController: NavController
) {
    val context = LocalContext.current

    var capaState by remember {
        mutableStateOf("")
    }

    var checkState by remember {
        mutableStateOf(false)
    }

    var titleState = remember {
        mutableStateOf("")
    }
    addDataAnnouncement(
        context = LocalContext.current,
        announcementID = 0
    ) {
        titleState = it
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
        mutableStateOf("")
    }
    var epubState by remember {
        mutableStateOf("")
    }
    var mobiState by remember {
        mutableStateOf("")
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
    var parentalRatings by remember {
        mutableStateOf(listOf<Classificacao>())
    }
    var idParentalRatings by remember {
        mutableStateOf(13)
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

    var checkSinopse by remember {
        mutableStateOf(false)
    }

    //Focus Requesters
    val titleFocusRequester = remember{
        FocusRequester()
    }
    val priceFocusRequester = remember{
        FocusRequester()
    }
    val sinopseFocusRequester = remember{
        FocusRequester()
    }
    val volFocusRequester = remember{
        FocusRequester()
    }
    val pagesFocusRequester = remember{
        FocusRequester()
    }

    var capaUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val selectImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        capaUri = uri

        updateStorage(capaState)
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

        updateStorage(pdfState)
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

        updateStorage(epubState)
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

        updateStorage(mobiState)
        Log.i("uri mobi", uri.toString())
    }

    val userIDRepository = UserIDrepository(context).getAll()

    CallAnnouncementAPI.getAnnouncement(announcementID, userIDRepository[0].idUser) {
        capaState = it.capa
        pdfState = it.pdf
        epubState = it.epub
        mobiState = it.mobi ?: ""
//        titleState = it.titulo
        priceState = it.preco.toString()
        sinopseState = it.sinopse
        volumeState = it.volume.toString()
        pagesState = it.qunatidadePaginas.toString()
        idParentalRatings = it.classificacao[0].idClassificacao!!

        if (getName(pdfState) != "0") pdfName = "${getName(pdfState).split(".pdf-")[0]}.pdf"
        if (getName(epubState) != "0") epubName = "${getName(epubState).split(".epub-")[0]}.epub"
        if (getName(mobiState) != "0") mobiName = "${getName(mobiState).split(".mobi-")[0]}.mobi"
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
                horizontalArrangement = Arrangement.SpaceBetween,
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
                        text = "Edite o seu E-book",
                        Modifier.fillMaxWidth(),
                        fontFamily = Spartan,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }

                if(!expandedTopBarState){
                    Card(
                        modifier = Modifier
                            .height(40.dp)
                            .clickable {
                                expandedTopBar = true
                                expandedTopBarState = true
                            }
                            .width(40.dp),
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,

                        ){
                        Icon(
                            Icons.Outlined.MoreVert, contentDescription = "",
                            modifier = Modifier.size(30.dp),
                            tint = Color(0xff381871)
                        )
                    }
                }

                val items1 = listOf("Desativar Ebook", "Apagar Ebook")

                DropdownMenu(
                    expanded = expandedTopBar,
                    onDismissRequest = {
                        expandedTopBar = false
                        expandedTopBarState = false
                    },
                    modifier = Modifier.fillMaxWidth(.85f)
                ) {
                    items1.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = index
                                expandedTopBar = false
                                expandedTopBarState = false

//                                if (index == 0) CallAnnouncementAPI
                                if (index == 1) showDialog = true
                            }
                        ) {
                            Text(text = item)
                        }
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Deseja mesmo excluir essa publicação?") },
                        text = { Text(text = "Essa ação é irreversível e resultará na exclusão completa da publicação dentro da plataforma.",
                            fontFamily = Spartan,
                            fontSize = 14.sp) },
                        confirmButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text(text = "Cancelar",
                                    fontFamily = Spartan
                                )
                            }
                            Button(
                                onClick = {
                                    CallAnnouncementAPI.deleteAnnouncement(announcementID) {
                                        if (it == 200) {
                                            updateStorage(capaState)

                                            updateStorage(pdfState)
                                            updateStorage(epubState)
                                            if (mobiState.isNotEmpty()) updateStorage(mobiState)

                                            navController.navigate(Routes.UserStories.name)
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white)),
                                elevation = ButtonDefaults.elevation(0.dp)
                            ) {
                                Text("Apagar",
                                    fontFamily = Spartan,
                                    color = Color.Red)

                            }
                        }
                    )
                }
            }


        }


        Row(
            Modifier.padding(20.dp, 12.dp)
        ) {


            Image(
                painter = rememberAsyncImagePainter(capaState),
                contentDescription = "capa do seu livro",
                modifier = Modifier
                    .height(160.dp)
                    .width(120.dp)
                    .padding(start = 8.dp, top = 8.dp)
                    .clickable {
                        selectImage.launch("image/*")
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        colorResource(id = R.color.eulirio_purple_text_color_border),
                        RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            //column titulo
            Column() {

                TextField(
                    modifier = Modifier
                        .focusRequester(titleFocusRequester),
                    value = titleState.value,
                    onValueChange = {
                        titleState.value = it
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
                    .fillMaxWidth()
                    .focusRequester(sinopseFocusRequester),
                label = {
                    Text(
                        text = stringResource(id = R.string.sinopsepub),
                        color = colorResource(id = R.color.eulirio_purple_text_color_border),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                singleLine = false,
                isError = checkSinopse,
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
                            text = parentalRatings[idParentalRatings - 13].classificacao,
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
                    volumeState = it
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
                    pagesState = it
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
                if (state) generos += Genero(idGenero)

                else generos -= Genero(idGenero)
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


            var capaCheck by remember {
                mutableStateOf(true)
            }
            var pdfCheck by remember {
                mutableStateOf(true)
            }
            var epubCheck by remember {
                mutableStateOf(true)
            }
            var mobiCheck by remember {
                mutableStateOf(true)
            }

            Card(
                backgroundColor = colorResource(id = R.color.eulirio_purple_text_color_border),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        //Verificação se o titulo está vazio
                        if (titleState.value.isEmpty()) {
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

                        if (sinopseState.isEmpty()) {
                            checkSinopse = true
                            sinopseFocusRequester.requestFocus()
                        } else checkSinopse = false

                        //Verificação se o volume esta vazio
                        if (volumeState.isEmpty()) {
                            checkVol = true
                            volFocusRequester.requestFocus()
                        } else {
                            checkVol = false
                        }

                        //Verificação se o campo de páginas esta vazio
                        if (pagesState.isEmpty()) {
                            checkPages = true
                            pagesFocusRequester.requestFocus()
                        } else {
                            checkPages = false
                        }

                        if (!checkTitle && !checkPrice && !checkSinopse && !checkClassification && !checkPages && !checkPDF && !checkEPUB && !checkFoto) {
                            if (capaUri != null) {
                                capaCheck = false

                                uploadFile(
                                    capaUri!!,
                                    "cover",
                                    titleState.value,
                                    context
                                ) {
                                    capaState = it
                                    capaCheck = true
                                }
                            }

                            if (pdfUri != null) {
                                pdfCheck = false

                                uploadFile(pdfUri!!, "file", "$pdfName-", context) {
                                    pdfState = it
                                    pdfCheck = true
                                }
                            }

                            if (epubUri != null) {
                                epubCheck = false

                                uploadFile(
                                    epubUri!!,
                                    "file",
                                    "$epubName-",
                                    context
                                ) {
                                    epubState = it
                                    epubCheck = true
                                }
                            }

                            if (mobiUri != null) {
                                mobiCheck = false

                                uploadFile(
                                    mobiUri!!,
                                    "file",
                                    "$mobiName-",
                                    context
                                ) {
                                    mobiState = it
                                    mobiCheck = true
                                }
                            }

                            if (capaCheck && pdfCheck && epubCheck && mobiCheck) {
                                val announcement = AnnouncementPost(
                                    titulo = titleState.value,
                                    volume = volumeState.toInt(),
                                    capa = capaState,
                                    sinopse = sinopseState,
                                    qunatidadePaginas = pagesState.toInt(),
                                    preco = priceState
                                        .replace(',', '.')
                                        .toFloat(),
                                    idClassificacao = idParentalRatings,
                                    idUsuario = userIDRepository[0].idUser,
                                    epub = epubState,
                                    pdf = pdfState,
                                    mobi = if (mobiState.isNotEmpty()) mobiState else null,
                                    generos = generos,
                                )

                                CallAnnouncementAPI.updateAnnouncement(
                                    announcementID,
                                    announcement
                                ) {
                                    if (it == 200) {
                                        navController.navigate(Routes.Home.name)
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
                        text = "SALVAR ALTERAÇÕES",
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
fun DefaultPreview8() {
    LoginPageTheme {
        val navController = rememberNavController()
        EditDataEbook(0, navController)
    }
}