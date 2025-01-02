package xyz.themanusia.pkm.ui.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.themanusia.pkm.R
import xyz.themanusia.pkm.model.UserResponse
import xyz.themanusia.pkm.ui.main.MainActivity
import xyz.themanusia.pkm.ui.theme.Silver
import xyz.themanusia.pkm.utils.ResultState
import java.util.UUID

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun Homepage(viewModel: HomepageViewModel = hiltViewModel()) {

    val scope = rememberCoroutineScope()

    val res = viewModel.res.value

    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }

    val loading = remember { mutableStateOf(false) }

    if (showDialog.value) {
        val name = remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch(Dispatchers.Main) {
                        viewModel.insert(
                            UserResponse.UserItems(
                                name = name.value, uuid = UUID.randomUUID().toString()
                            )
                        ).collect {
                            when (it) {
                                is ResultState.Success -> {
                                    showDialog.value = false
                                    loading.value = false
                                }

                                is ResultState.Failure -> {
                                    showDialog.value = false
                                    loading.value = false
                                }

                                ResultState.Loading -> {
                                    loading.value = true
                                }
                            }

                        }
                    }
                }) {
                    Text(text = "Tambah")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                }) {
                    Text(text = "Batal")
                }
            },
            title = {
                Text(text = "Tambah Pasien")
            },
            text = {
                Column {
                    TextField(value = name.value, onValueChange = { s -> name.value = s })
                }
            },
        )
    }

    BoxWithConstraints {
        Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog.value = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah User")
            }
        }) { innerPadding ->
            BoxWithConstraints(Modifier.fillMaxSize()) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.neu_rehab_text),
                            contentDescription = "Neu Rehab",
                            modifier = Modifier.padding(70.dp)
                        )
                        if (res.item.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(top = 50.dp)
                                    .fillMaxWidth(),
                            ) {
                                items(res.item) { user ->
                                    Surface(
                                        onClick = {
                                            val intent = Intent(context, MainActivity::class.java)
                                            intent.putExtra("user", user.key)
                                            context.startActivity(intent)
                                        },
                                        color = Color.Transparent,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(color = Silver)
                                                .fillMaxWidth()
                                                .height(2.dp)
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    start = 30.dp,
                                                    end = 30.dp,
                                                    top = 24.dp,
                                                    bottom = 24.dp
                                                )
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.user_icon),
                                                contentDescription = "Icon",
                                                modifier = Modifier.size(88.dp)
                                            )
                                            Text(
                                                text = user.item?.name ?: "",
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                                fontSize = 20.sp,
                                                modifier = Modifier
                                                    .padding(start = 25.dp)
                                                    .weight(1f)
                                            )
                                            IconButton(
                                                onClick = {
                                                    scope.launch(Dispatchers.Main) {
                                                        viewModel.delete(user.key ?: "")
                                                            .collect {
                                                                when (it) {
                                                                    is ResultState.Success -> {
                                                                        Toast.makeText(
                                                                            context,
                                                                            it.data,
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                        loading.value = false
                                                                    }

                                                                    is ResultState.Failure -> {
                                                                        loading.value = false
                                                                    }

                                                                    ResultState.Loading -> {
                                                                        loading.value = true
                                                                    }

                                                                }
                                                            }
                                                    }
                                                }) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Delete"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (res.isLoading || loading.value) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}