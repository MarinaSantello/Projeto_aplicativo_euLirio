package com.example.loginpage

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.loginpage.API.announcement.CallAnnouncementAPI
import com.example.loginpage.API.dashboard.CallDashboardAPI
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.DadosVendas
import com.example.loginpage.models.Dashboard
import com.example.loginpage.models.Dias
import com.example.loginpage.resources.DrawerDesign
import com.example.loginpage.ui.components.Charts.CreateLineChart
import com.example.loginpage.ui.components.Charts.PieChart
import com.example.loginpage.ui.components.DashCommentCard
import com.example.loginpage.ui.theme.LoginPageTheme
import com.example.loginpage.ui.theme.SpartanBold
import kotlin.math.ceil

data class Cards(
    val title: String,
    val data: Int
)

class DashboardPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowDashboard(navController = rememberNavController(), idAnnouncement = 16)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowDashboard (
    navController: NavController,
    idAnnouncement: Int
) {
    var dashboard by remember {
        mutableStateOf<Dashboard?>(null)
    }

    CallDashboardAPI.getDataDashboard(idAnnouncement) {
        dashboard = it
    }

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()

    val topBarState = remember { mutableStateOf(true) }

    // registrando o id do usuário no sqlLite
    val userIDRepository = UserIDrepository(context)
    val users = userIDRepository.getAll()
    val userID = UserID(id = users[0].id, idUser = users[0].idUser)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { com.example.loginpage.resources.TopBar(userID, scaffoldState, topBarState) },
        drawerContent = {
            DrawerDesign(userID, context, scaffoldState, navController)
        },
    ) {
        if(dashboard != null) DashboardView(dashboard!!, userID.idUser)
    }
}

@Composable
fun DashboardView(
    dashboard: Dashboard,
    userID: Int
) {

    var title by remember {
        mutableStateOf("")
    }

    dashboard.compras.idAnnoncement?.let { CallAnnouncementAPI.getAnnouncement(it, userID){
        title = it.titulo
    } }

    val chatLineDates by remember {
        mutableStateOf(dashboard.vendasSemana.dates)
    }

    val chatLineSales by remember {
        mutableStateOf(dashboard.vendasSemana.sales)
    }

    val chatLineInvoicing by remember {
        mutableStateOf(dashboard.vendasSemana.invoicing)
    }

    val maxValue by remember {
        mutableStateOf(dashboard.roundedData)
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp),
            text = "titulo do livro",
            fontFamily = SpartanBold,
            fontSize = 32.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.ShoppingBag,
                contentDescription = "Icone de bolsa",
                modifier = Modifier.height(24.dp),
                tint = colorResource(id = R.color.eulirio_black)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Vendas semanais",
                fontFamily = SpartanBold,
                fontSize = 20.sp
            )
        }

        CreateLineChart(maxValue, chatLineDates, chatLineInvoicing)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.ChatBubbleOutline,
                contentDescription = "Icone de comentario",
                modifier = Modifier.height(24.dp),
                tint = colorResource(id = R.color.eulirio_black)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Avaliações (isncs)",
                fontFamily = SpartanBold,
                fontSize = 20.sp
            )
        }
        DashCommentCard(3.4, listOf(12.5, 75.4, 0.5, 1.6, 5.0))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.Person,
                contentDescription = "Icone de perfil",
                modifier = Modifier.height(24.dp),
                tint = colorResource(id = R.color.eulirio_black)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Perfil de consumidores",
                fontFamily = SpartanBold,
                fontSize = 20.sp
            )
        }
        PieChart(
            data = mapOf(
                Pair("Leitores", 70),
                Pair("Autores", 30)
            ) )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.TrendingUp,
                contentDescription = "Icone de perfil",
                modifier = Modifier.height(24.dp),
                tint = colorResource(id = R.color.eulirio_black)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Suas conquistas",
                fontFamily = SpartanBold,
                fontSize = 20.sp
            )
        }
        val cards = listOf(Cards("favoritos", 123), Cards("curtidas", 123), Cards("compras", 123), Cards("leituras", 123), Cards("recomendações", 123), Cards("favoritos", 123))
        LazyRow(contentPadding = PaddingValues(top = 12.dp, start = 16.dp, bottom = 24.dp)){
            items(cards) {
                val icon = when (it.title) {
                    "favoritos" -> Icons.Filled.Bookmark
                    "curtidas" -> Icons.Filled.Favorite
                    "compras" -> Icons.Filled.Paid
                    "leituras" -> Icons.Filled.CheckCircle
                    "recomendações" -> Icons.Filled.LocalLibrary
                    else -> Icons.Filled.Spa
                }
                Card(
                    Modifier
                        .size(120.dp)
                        .padding(4.dp, 0.dp),
                    backgroundColor = Color(0xFFFEFCF1)
                ) {
                    Row() {
                        Text(text = it.title)

                        Icon(
                            icon,
                            contentDescription = "Icone de perfil",
                            modifier = Modifier.height(24.dp),
                            tint = colorResource(id = R.color.eulirio_black)
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview5() {
//    LoginPageTheme {
//        Greeting2("Android")
//    }
//}