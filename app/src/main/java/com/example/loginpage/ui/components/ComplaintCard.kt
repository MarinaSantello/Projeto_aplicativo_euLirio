package com.example.loginpage.ui.components

import androidx.compose.runtime.Composable
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.API.complaint.CallComplaintAPI
import com.example.loginpage.R
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.models.*
import com.example.loginpage.ui.theme.Spartan
import com.example.loginpage.ui.theme.SpartanBold
import com.example.loginpage.ui.theme.SpartanExtraLight
import com.example.loginpage.ui.theme.SpartanRegular

@Composable
fun ComplaintCard(
    showDialog: MutableState<Boolean>,
    userID: Int,
    complaintID: Int,
    typeComplaint: Int,
    apiResponse: (Boolean) -> Unit
) {
    var textState by remember { mutableStateOf("") }

    var typesComplaint by remember { mutableStateOf(listOf<ComplaintType>()) }
    var typesComplained by remember { mutableStateOf(listOf<IdComplaintType>()) }

    CallComplaintAPI.getComplaintTypes {
        typesComplaint = it
    }

    if (showDialog.value) AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onDismissRequest = { showDialog.value = !showDialog.value },
        title = {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Qual o problema?",
                    fontSize = 20.sp,
                    fontFamily = SpartanBold
                )
            }
        },
        text = {
            Text(text = "")
            MaterialTheme {
                Column(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LazyColumn() {
                        items(typesComplaint) {typeComplaint ->
                            var checkedState by remember { mutableStateOf(false) }

                            Row(
                                modifier = Modifier.clickable { checkedState = !checkedState },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checkedState,
                                    onCheckedChange = { isChecked ->
                                        checkedState = isChecked
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.eulirio_red))
                                )
                                Text(
                                    text = typeComplaint.tipo,
                                    fontSize = 16.sp,
                                    fontFamily = SpartanRegular
                                )
                            }

                            if(checkedState) typesComplained += IdComplaintType(typeComplaint.id)
                            else typesComplained -= IdComplaintType(typeComplaint.id)
                        }
                    }
                    TextField(
                        value = textState,
                        onValueChange = { textState = it },
                        placeholder = {
                            Text(
                                "Ajude a moderação da plataforma denunciando publicações e/ou usuários irregulares.",
                                fontFamily = SpartanExtraLight,
                                fontSize = 16.sp
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.eulirio_black),
                            unfocusedBorderColor = androidx.compose.ui.graphics.Color.Gray,
                            cursorColor = colorResource(id = R.color.eulirio_black)
                        )
                    )
                }
            }
        },

        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(androidx.compose.ui.graphics.Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = {
                    when (typeComplaint) {
                        0 -> {
                            val complaintAnnouncement = ComplaintAnnoncement(textState, complaintID, typesComplained)

                            CallComplaintAPI.reportAnnouncement(userID, complaintAnnouncement) {
                                apiResponse.invoke(it == 201)
                            }
                        }
                        1 -> {
                            val complaintShortStory = ComplaintShortStory(textState, complaintID, typesComplained)

                            CallComplaintAPI.reportShortStory(userID, complaintShortStory) {
                                apiResponse.invoke(it == 201)
                            }
                        }
                        2 -> {
                            val complaintUser = ComplaintUser(textState, complaintID, typesComplained)

                            CallComplaintAPI.reportUser(userID, complaintUser) {
                                apiResponse.invoke(it == 201)
                            }
                        }
                        3 -> {
                            val complaintRecommendation = ComplaintRecommendation(textState, complaintID, typesComplained)

                            CallComplaintAPI.reportRecommendation(userID, complaintRecommendation) {
                                apiResponse.invoke(it == 201)
                            }
                        }
                    }
                },
                ) {
                Text("Denunciar",
                    color = colorResource(id = R.color.eulirio_red)
                )
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(androidx.compose.ui.graphics.Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = {
                    showDialog.value = !showDialog.value
                },
            ) {
                Text("Cancelar")
            }
        }
    )
}