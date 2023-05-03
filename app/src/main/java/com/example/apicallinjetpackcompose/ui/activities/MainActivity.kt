package com.example.apicallinjetpackcompose.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Dao
import com.example.apicallinjetpackcompose.database.Database
import com.example.apicallinjetpackcompose.database.MyDao
import com.example.apicallinjetpackcompose.ui.theme.ApiCallInJetpackComposeTheme
import com.example.apicallinjetpackcompose.viewmodel.NiceViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private lateinit var viewModel: NiceViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiCallInJetpackComposeTheme {
                viewModel = ViewModelProvider(this)[NiceViewModel::class.java]
                this.viewModelStore
                var isResponseVisible by remember { mutableStateOf(false) }
                val database = Database.getDatabase(applicationContext)
                val dao = database.dao()

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    var title by remember {
                        mutableStateOf("")
                    }

                    var body by remember {
                        mutableStateOf("")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {

                        OutlinedTextField(
                            value = title,
                            onValueChange = { text ->
                                title = text
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black,
                            ),
                            placeholder = {
                                Text(
                                    text = "Enter Title",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            },
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold, // set the font weight to bold
                            ),
                            modifier = Modifier
                                .weight(1f) // it means outlinedTF will fill all the space it can get
                                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                        )

                        OutlinedTextField(
                            value = body,
                            onValueChange = { text ->
                                body = text
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Gray,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black,
                            ),
                            placeholder = {
                                Text(
                                    text = "Enter Body",
                                    style = TextStyle(
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            },
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold, // set the font weight to bold
                            ),
                            modifier = Modifier
                                .weight(1f) // it means outlinedTF will fill all the space it can get
                                .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)

                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {

                        Button(
                            onClick = {
                                if (title.isEmpty() || body.isEmpty()) {
                                    Toast.makeText(applicationContext, "No Empty Fields Allowed!", Toast.LENGTH_SHORT).show()
                                } else {
                                    makeApiCall(title, body)
                                }
                            },

                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),

                            colors = ButtonDefaults.buttonColors(Color(0xFF2E8BC0))

                        ) {
                            Text(text = "Make Post Api Call", color = Color.White)
                        }

                        Button(
                            onClick = { makeGetApiCall() },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),

                            colors = ButtonDefaults.buttonColors(Color(0xFF2E8BC0))

                        ) {
                            Text(text = "Make Get Api Call", color = Color.White)
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {
                        var resOfPost by remember {
                            mutableStateOf("")
                        }

                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.IO){
                                        dao.postRequestSavedResponse().collect {
                                            Log.d(TAG, "onCreate: $it")
                                            resOfPost = it.toString()
                                            withContext(Dispatchers.Main){
                                                Toast.makeText(applicationContext, resOfPost, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            },

                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),

                            colors = ButtonDefaults.buttonColors(Color(0xFF2E8BC0))

                        ) {
                            Text(text = "Open Post DB", color = Color.White)
                        }

                        Button(
                            onClick = {
                                startActivity(Intent(this@MainActivity, GetResponseActivity::class.java))
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),

                            colors = ButtonDefaults.buttonColors(Color(0xFF2E8BC0))

                        ) {
                            Text(text = "Open Get DB", color = Color.White)
                        }
                    }

                    val postResponse by viewModel.postRequestObserver.observeAsState()
                    val getResponse by viewModel.getRequestObserver.observeAsState()

                    Text(
                        text = postResponse?.toString() ?: "",
                        modifier = Modifier.clickable {
                            postResponse?.let {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.IO){
                                        dao.insertPostResponse(it)
                                        Log.d(TAG, "onCreate: Added To DB")
                                    }
                                }
                            }
                        }
//                        modifier = Modifier.visibility(if (response != null) Visibility.Visible else Visibility.Hidden)

                    )

                    LazyColumn{
                        getResponse?.let { responseFromGetAPI ->
                            items(responseFromGetAPI){ response ->
                                val title = response.title
                                val imageUrl = response.thumbnailUrl

                                Text(
                                    text = title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )

//                                https://github.com/skydoves/landscapist use it for GlideImage ref

                                GlideImage(
                                    imageModel = { imageUrl }, // loading a network image using an URL.
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    ),

                                    modifier = Modifier.clickable {
                                        lifecycleScope.launch {
                                            withContext(Dispatchers.IO){
                                                dao.insertGetResponseItem(response)
                                            }
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.size(20.dp))

                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }


    private fun makeApiCall(title: String, body: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.makeApiCall(title, body)
        }
    }

    private fun makeGetApiCall() {
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.makeGetApiCall()
        }
    }
}