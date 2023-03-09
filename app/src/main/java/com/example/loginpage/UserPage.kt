import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme

class UserPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    UserHomePage()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)

@Composable
fun UserHomePage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp)
                .shadow(55.dp),
            shape = RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp),
            backgroundColor = colorResource(id = R.color.eulirio_yellow_card_background),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(top = 10.dp, start = 20.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = "flecha para esquerda",
                    modifier = Modifier
                        .height(24.dp)
                        .width(30.dp)

                )
            }


            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(300.dp)
                    .padding(start = 41.dp, top = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_icon_user_eulirio_example_background),
                    contentDescription = "",
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(100.dp))


                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, bottom = 10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Noah Sebastian", fontSize = 18.sp, fontWeight = FontWeight.SemiBold

                    )

                    Text(
                        text = "@n.sebastian",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(bottom = 4.dp)

                    )

                    Card(
                        modifier = Modifier
                            .height(12.dp)
                            .width(80.dp),
                        backgroundColor = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(100.dp),

                        ) {
                        Text(
                            text = "EDITAR PERFIL",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            modifier = Modifier

                        )
                    }


                }

            }


            Row(

                modifier = Modifier
                    .padding(start = 41.dp)
                    .width(169.dp)
                    .height(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {

                //Contador de obras
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .height(50.dp)
                        .width(33.dp)
                ) {
                    Text(
                        text = "182",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold


                    )
                    Text(
                        text = "OBRAS",
                        fontSize = 8.sp,
                        modifier = Modifier.padding(bottom = 5.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light
                    )

                }

                //Contador de seguindo
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(50.dp)
                        .width(52.dp)
                ) {
                    Text(
                        text = "570",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold

                    )
                    Text(
                        text = "SEGUINDO",
                        fontSize = 8.sp,
                        modifier = Modifier.padding(bottom = 5.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light
                    )

                }

                //Contador de seguidores
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(50.dp)
                        .width(52.dp)
                ) {
                    Text(
                        text = "570",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold

                    )
                    Text(
                        text = "SEGUIDORES",
                        fontSize = 8.sp,
                        modifier = Modifier.padding(bottom = 5.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light
                    )

                }


            }

        }



        //Coluna para a biografia do autor
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier


        ){

        }


    }

}


@Composable
fun UserPagePreview() {
    UserPagePreview()
}