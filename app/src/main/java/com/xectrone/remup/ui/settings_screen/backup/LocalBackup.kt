package com.xectrone.remup.ui.settings_screen.backup

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import com.xectrone.remup.ui.theme.Constants
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Timer
import kotlin.concurrent.schedule

object LocalBackup {
    fun backupDatabase(context: Context) {
        val dbName = "database" // Your database name
        val dbFile = context.getDatabasePath(dbName) // Database file path
        val backupFile = File(context.getExternalFilesDir(null), "${Constants.APP_NAME}-backup.${Constants.APP_NAME}") // Backup file path

        try {
            // Open streams for copying the file
            FileInputStream(dbFile).use { input ->
                FileOutputStream(backupFile).use { output ->
                    input.copyTo(output) // Copy the file content
                }
            }
            shareBackupFile(context, backupFile)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to backup database: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareBackupFile(context: Context, backupFile: File) {
        // Create a URI for the backup file
        val backupUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider", // Change to your app's applicationId
            backupFile
        )
        // Create a share intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/octet-stream" // MIME type for binary files
            putExtra(Intent.EXTRA_STREAM, backupUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant temporary read permission to the content URI
        }
        // Start the share intent
        context.startActivity(Intent.createChooser(shareIntent, "Share Database Backup"))

        // Schedule the file deletion after a delay (e.g., 5 seconds)
        Timer().schedule(1000*60*2) { // 5000 milliseconds = 5 seconds
            if (backupFile.exists()) {
                backupFile.delete()
            }
        }
    }

    fun importBackupFile(uri: Uri, context: Context) {
        val dbName = "database" // Replace with your database name
        val dbFile = context.getDatabasePath(dbName)

        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }
            Toast.makeText(context, "Database successfully imported.", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to import database.", Toast.LENGTH_SHORT).show()
        }
    }
}