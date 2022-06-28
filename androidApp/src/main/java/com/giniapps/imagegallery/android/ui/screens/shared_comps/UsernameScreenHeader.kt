package com.giniapps.imagegallery.android.ui.screens.shared_comps

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giniapps.imagegallery.android.R

@Composable
fun UsernameScreenHeader(
    modifier: Modifier = Modifier,
    loggedUserName: String,
    onLogout: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = loggedUserName
        )
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = {
                onLogout()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.error
            )
        ) {
            Text(text = stringResource(id = R.string.logout))
        }
    }
}