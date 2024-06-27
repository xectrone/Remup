package com.xectrone.remup.ui.settings_screen.backup

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.xectrone.remup.ui.theme.Constants
import com.xectrone.remup.ui.theme.CustomShape
import com.xectrone.remup.ui.theme.CustomTypography
import com.xectrone.remup.ui.theme.Dimen
import com.xectrone.remup.ui.theme.LocalCustomColorPalette
import kotlinx.coroutines.launch

@Composable
fun LocalBackupScreenSection() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val dirPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { maybeUri ->
            maybeUri?.let { uri ->
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flags)
                Log.d("#TAG", "LocalBackupScreenSection: ${uri.path}")

                val mimeType = context.contentResolver.getType(uri)
                if (mimeType == "application/octet-stream" && uri.path?.endsWith(".${Constants.APP_NAME}") == true)
                    scope.launch {
                        LocalBackup.importBackupFile(uri, context)
                    }
                else
                    Toast.makeText(context, "Please select a valid Backup file", Toast.LENGTH_SHORT).show()



            }
        }
    )

    Column(
        modifier = Modifier
            .padding(Dimen.Padding.p4)
    ) {
        //region - Shortcut -

        Text(
            modifier = Modifier.padding(vertical = Dimen.Padding.p2),
            text = "Local Backup",
            style = CustomTypography.titleSecondary,
            color = LocalCustomColorPalette.current.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(bottom = Dimen.Padding.p2),
                text = "Export Database",
                style = CustomTypography.textPrimary,
                color = LocalCustomColorPalette.current.tertiary
            )
            Button(
                modifier = Modifier
                    .padding(bottom = Dimen.Padding.p2),
                shape = CustomShape.button,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorPalette.current.accentSecondary,
                    contentColor = LocalCustomColorPalette.current.background
                ),
                onClick = {
                    LocalBackup.backupDatabase(context)
                }
            ) {
                Text(text = "Export")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(bottom = Dimen.Padding.p2),
                text = "Import Database",
                style = CustomTypography.textPrimary,
                color = LocalCustomColorPalette.current.tertiary
            )
            Button(
                modifier = Modifier
                    .padding(bottom = Dimen.Padding.p2),
                shape = CustomShape.button,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorPalette.current.accentSecondary,
                    contentColor = LocalCustomColorPalette.current.background
                ),
                onClick = {
                    dirPickerLauncher.launch(arrayOf("*/*"))
                }
            ) {
                Text(text = "Import")
            }
        }
//        Divider(
//            modifier = Modifier.padding(vertical = Dimen.Padding.p2),
//            color = LocalCustomColorPalette.current.tertiary
//        )
        //endregion
    }
}