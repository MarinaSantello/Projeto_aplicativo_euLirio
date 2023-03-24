package com.example.loginpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.models.Genero
import com.example.loginpage.ui.components.GenreCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.google.firebase.storage.FirebaseStorage

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

    private fun uploadFile() {

        var t: String = ""

        if (filePath != null) {

            //progressBar = findViewById(R.id.progressbar)
            progressBar.visibility = View.VISIBLE

            var imageRef = FirebaseStorage
                .getInstance()
                .reference
                .child("images/teste")

            imageRef.putFile(filePath)
                .addOnSuccessListener { p0 ->
                    //pd.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.INVISIBLE

                    imageRef
                        .downloadUrl
                        .addOnSuccessListener { uri ->
                            edUri.setText(uri.toString())
                        }
                }
                .addOnFailureListener { p0 ->
                    Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { p0 ->
                    var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    //pd.setMessage("Uploaded ${progress.toInt()}%")
                }
        }

        Log.d("Teste", "Uri: $t")
    }

    private fun startFileChooser() {
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose Picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("MainActivity", resultCode.toString())

        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
            //var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            //ivImage.setImageBitmap(bitmap)
            ivImage.setImageURI(filePath)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdatePage() {
    val context = LocalContext.current

    var checkState by remember {
        mutableStateOf(false)
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

    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .height(48.dp)
                    .padding(start = 12.dp)
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
                                .width(80.dp),
                            shape = RoundedCornerShape(35.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"),
                                contentDescription = "sua foto de perfil"
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column() {
                            TextField(
                                value = nameState,
                                modifier = Modifier.height(30.dp),
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
                                    value = nameState,
                                    modifier = Modifier
                                        .height(30.dp),
                                    onValueChange = {
                                        nameState = it
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
                            value = nameState,
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth(),
                            onValueChange = {
                                nameState = it
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
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                            .border(0.5.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp, top = 5.dp),
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
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = writerCheckState,
                                    onCheckedChange = {
                                        writerCheckState = it
                                    },
                                    modifier = Modifier
                                        .height(10.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.tag_1_name).uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = readerCheckState,
                                    onCheckedChange = {
                                        readerCheckState = it
                                    }
                                )
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
                                shape = RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth()
                    ) {


                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 5.dp),
                            text = "Generos",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(4),

                                // content padding
                                contentPadding = PaddingValues(
                                    start = 9.dp,
                                    end = 12.dp,
                                    top = 5.dp,
                                    bottom = 8.dp
                                )
                            ) {
                               
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
                            onClick = { /*TODO*/ },
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
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, colorResource(id = R.color.eulirio_red)),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.eulirio_light_yellow_background)),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_delete_account).uppercase(),
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