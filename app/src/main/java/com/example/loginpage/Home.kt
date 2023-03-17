package com.example.loginpage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loginpage.ui.theme.LoginPageTheme
import kotlinx.coroutines.launch

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    homeBooks()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun homeBooks() {

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }
    val bottomBarState = remember { mutableStateOf(true) }
    val fabState = remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (!fabState.value)
                            fabState.value = !fabState.value
                    }
                )
            },
        scaffoldState = scaffoldState,
        topBar = { TopBar(scaffoldState, topBarState) },
        bottomBar = { BottomBar(bottomBarState) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (fabState.value) {
                FloatingActionButton(scaffoldState) {
                    fabState.value = it
                }
            }
            else {
                Column() {
                    Button(onClick = { /* do something */ }) {
                        Text(text = "Button 1")
                    }
                    Button(onClick = { /* do something */ }) {
                        Text(text = "Button 2")
                    }
                }
            }
        },
        drawerContent = {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
        },
        drawerGesturesEnabled = true,
    ) {
        Text(text = "teste $it")
    }
}

@Composable
fun TopBar(
    scaffoldState: ScaffoldState,
    state: MutableState<Boolean>
) {
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = state.value,
        modifier = Modifier.background(Color.Red),
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = { Text("Top Bar") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun BottomBar(state: MutableState<Boolean>) {
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomAppBar{Text("Bottom Bar")}
        }
    )
}

@Composable
fun FloatingActionButton(
    scaffoldState: ScaffoldState,
    onChecked: (Boolean) -> Unit
) {

    var showAdditionalButtons by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    androidx.compose.material.FloatingActionButton(
        onClick = {
            onChecked.invoke(showAdditionalButtons)

            showAdditionalButtons = !showAdditionalButtons
//            coroutineScope.launch {
//                //.snackbarHostState.showSnackbar("FAB Action Button Clicked")
//            }
        }
    ) {
        Text("FAB")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginPageTheme {
        homeBooks()
    }
}