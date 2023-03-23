package com.example.loginpage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginpage.ui.theme.LoginPageTheme

class UpdateActivity : ComponentActivity() {
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
                                onValueChange = {
                                    nameState = it
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.name),
                                        color = Color(0xFF1E1E1E),
                                        fontWeight = FontWeight.Bold
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
                                Text(
                                    text = stringResource(id = R.string.user_name),
                                    fontSize = 6.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                OutlinedTextField(
                                    value = userNameState,
                                    onValueChange = {
                                        userNameState = it
                                    },
                                    modifier = Modifier
                                        .height(60.dp)
                                        .widthIn(200.dp, 250.dp)
                                        .background(colorResource(id = R.color.eulirio_light_yellow_background)),
                                    singleLine = false
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
                        Text(
                            text = stringResource(id = R.string.biography),
                            fontSize = 6.sp,
                            fontWeight = FontWeight.Bold
                        )

                        OutlinedTextField(
                            value = biographyState,
                            onValueChange = {
                                biographyState = it
                            },
                            modifier = Modifier
                                .heightIn(115.dp)
                                .fillMaxWidth()
                                .background(colorResource(id = R.color.eulirio_light_yellow_background)),
                            singleLine = false
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
                            text = "Você é...",
                            fontSize = 6.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
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
//                        Image(painterResource(id = R.drawable.tag), contentDescription = "")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Row() {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = false,
                                        onCheckedChange = {}
                                    )
                                    Text(
                                        text = "Terror".uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = false,
                                        onCheckedChange = {}
                                    )
                                    Text(
                                        text = "Drama".uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = false,
                                        onCheckedChange = {}
                                    )
                                    Text(
                                        text = "Romance".uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Text(
                                text = "Ver mais...",
                                fontSize = 6.sp
                            )
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

                //column alert dialog
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {


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
                                        modifier = Modifier.size(24.dp).padding(top = 4.dp),
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