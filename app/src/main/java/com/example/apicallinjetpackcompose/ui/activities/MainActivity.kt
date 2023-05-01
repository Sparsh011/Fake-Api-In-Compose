package com.example.apicallinjetpackcompose.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.apicallinjetpackcompose.ui.theme.ApiCallInJetpackComposeTheme
import com.example.apicallinjetpackcompose.viewmodel.NiceViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
                viewModel = ViewModelProvider(this).get(NiceViewModel::class.java)
                var isResponseVisible by remember { mutableStateOf(false) }

                makeGetApiCall()

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
                                .padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),

                            colors = ButtonDefaults.buttonColors(Color(0xFF2E8BC0))

                        ) {
                            Text(text = "Make Get Api Call", color = Color.White)
                        }
                    }

                    val postResponse by viewModel.postRequestObserver.observeAsState()
                    val getResponse by viewModel.getRequestObserver.observeAsState()

                    Text(
                        text = postResponse?.toString() ?: ""
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
                                    )
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