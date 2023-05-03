package com.example.apicallinjetpackcompose.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.apicallinjetpackcompose.api.GetRequestApiResponseItem
import com.example.apicallinjetpackcompose.database.Database
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetResponseActivity : ComponentActivity() {
    private val TAG = "GetResponseAct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = Database.getDatabase(applicationContext)
            val dao = database.dao()
            val savedResponse = remember {
                mutableStateListOf<GetRequestApiResponseItem>()
            }

            LaunchedEffect(Unit) {
                dao.getRequestSavedResponse().collect() {
                    savedResponse.addAll(it)
                }
            }

            LazyColumn {
                items(savedResponse) { response ->
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
                                withContext(Dispatchers.IO) {
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