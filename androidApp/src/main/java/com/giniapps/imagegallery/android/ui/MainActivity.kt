package com.giniapps.imagegallery.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.giniapps.imagegallery.android.R
import com.giniapps.imagegallery.android.ui.theme.ImageGalleryTheme
import com.giniapps.imagegallery.view_models.LoginViewModel
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageGalleryTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {

}

@Composable
fun LoginScreen(
    onLogin: () -> Unit
) {
    val viewModel: LoginViewModel = getViewModel()
    val users by viewModel.usersState.collectAsState()
    var selectedOption by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Column {
            DropdownTextField(
                options = users.map { it.email },
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it }
            )
            Button(
                onClick = {
                    val user = users.find { it.email == selectedOption }
                    user?.let {
                        viewModel.loginUser(it.id)
                        onLogin()
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownTextField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(
                        id = R.string.select_user
                    )
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach {
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(it)
                        expanded = false
                    }
                ) {
                    Text(text = it)
                }
            }
        }
    }
}
