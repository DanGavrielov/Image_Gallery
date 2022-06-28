package com.giniapps.imagegallery.android.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giniapps.imagegallery.android.R
import com.giniapps.imagegallery.view_models.LoginViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit
) {
    val viewModel: LoginViewModel = getViewModel()
    val users by viewModel.usersState.collectAsState()
    var selectedOption by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_title),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(24.dp))
        DropdownTextField(
            options = users.map { it.email },
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            modifier = Modifier.width(200.dp),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownTextField(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
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