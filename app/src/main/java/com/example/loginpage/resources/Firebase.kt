package com.example.loginpage.resources

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.example.loginpage.API.user.RetrofitApi
import com.example.loginpage.API.userLogin.UserLoginCall
import com.example.loginpage.MainActivity
import com.example.loginpage.SQLite.dao.repository.UserIDrepository
import com.example.loginpage.SQLite.model.UserID
import com.example.loginpage.constants.Routes
import com.example.loginpage.models.RetornoApi
import com.example.loginpage.models.UserLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


fun authenticate(
    email: String,
    password: String,
    context: Context
) {

    // obtendo a instancia do firebase
    val auth = FirebaseAuth.getInstance()

    // autenticação
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {

            if (it.isSuccessful) {
                val userLogin = UserLogin(
                    uid = it.result.user!!.uid
                )

                val retrofit = RetrofitApi.getRetrofit() // pegar a instância do retrofit
                val userLoginCall = retrofit.create(UserLoginCall::class.java) // instância do objeto contact
                val callValidateUser = userLoginCall.validate(userLogin)

                var responseValidate = 0
                // Excutar a chamada para o End-point
                callValidateUser.enqueue(object :
                    Callback<RetornoApi> { // enqueue: usado somente quando o objeto retorna um valor
                    override fun onResponse(
                        call: Call<RetornoApi>,
                        response: Response<RetornoApi>
                    ) {
                        responseValidate = response.code()

                        Log.i("erro fdp", responseValidate.toString())

                        if (responseValidate == 200){
                            // registrando o id do usuário no sqlLite
                            val userIDRepository = UserIDrepository(context)
                            userIDRepository.save(UserID(idUser = response.body()!!.id))

                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        }
                    }

                    override fun onFailure(
                        call: Call<RetornoApi>,
                        t: Throwable
                    ) {
                        val err = t.message
                        Log.i("teste login", err.toString())
                    }
                })
            }
            Log.i("resposta firebase", it.isSuccessful.toString()) // retorna um booleano com a autenticação
        }

}

fun updateStorage(photoState: String) {
    if ("firebase" in photoState) {
        val storageRef = FirebaseStorage
            .getInstance()
            .getReferenceFromUrl(photoState)

        storageRef.delete()
            .addOnSuccessListener {
                Log.i("att foto perfil", "parabens pelo minimo")
            }
            .addOnFailureListener { e ->
                Log.i("att foto perfil", "$e")
                // An error occurred while updating the file
            }
    }
}


fun getName(fileStorage: String): String {
    return if ("firebase" in fileStorage) FirebaseStorage
            .getInstance()
            .getReferenceFromUrl(fileStorage)
            .name

    else "0"
}

fun uploadFile(file: Uri, folder: String, fileName: String, context: Context, uri: (String) -> Unit) {

    val datetime = LocalDateTime.now().toString()
    val discriminante = datetime.replace(' ', '-')

    //progressBar = findViewById(R.id.progressbar)
    //progressBar.visibility = View.VISIBLE

    val imageRef = FirebaseStorage
        .getInstance()
        .reference
        .child("$folder/$discriminante||$fileName")

    if(file.toString().isNotEmpty()) imageRef.putFile(file)
        .addOnSuccessListener { p0 ->
            imageRef
                .downloadUrl
                .addOnSuccessListener {
                    uri.invoke(it.toString())
                }
            Log.i("firebase upload", p0.toString())
        }
        .addOnFailureListener { p0 ->
            Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            Log.i("firebase upload", p0.toString())
        }
        .addOnProgressListener { p0 ->
            var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
            //pd.setMessage("Uploaded ${progress.toInt()}%")
        }
    else
        uri.invoke("")
}