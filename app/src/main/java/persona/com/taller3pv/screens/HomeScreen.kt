package persona.com.taller3pv.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import persona.com.taller3pv.components.CustomButton
import persona.com.taller3pv.components.CustomLabel
import persona.com.taller3pv.data.UserRepository
import java.util.Locale

/**
 * Pantalla principal que se muestra luego de iniciar sesión.
 *
 * @param userName nombre del usuario a mostrar en pantalla.
 * @param onLogoutClick se llama al presionar "Cerrar sesión".
 */
@Composable
fun HomeScreen(
    userName: String,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    var recognizerText by remember { mutableStateOf("") }
    var saveErrorMessage by remember { mutableStateOf("") }


    val speechRecognizer = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK){
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val texto = matches?.firstOrNull() ?: ""
            recognizerText = texto

            if(texto.isNotEmpty()){
                saveErrorMessage=""
                UserRepository.saveVoiceMessage(
                    userName = userName,
                    text = texto,
                    onSuccess = {},
                    onError = { saveErrorMessage = it }
                )
            }
        }
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            speechRecognizer.launch(crteateSpeechRecognizerIntent())
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomLabel(text = "¡Bienvenido!")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = userName,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Hablar",
            onClick = {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
                if(hasPermission){
                    speechRecognizer.launch(crteateSpeechRecognizerIntent())
                }else{
                    permissionsLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(recognizerText.isNotEmpty()){
            Text(
                text = "Dijiste :  $recognizerText",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if(saveErrorMessage.isNotEmpty()){
            Text(
                text = saveErrorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        CustomButton(
            text = "Cerrar sesión",
            onClick = onLogoutClick
        )
    }
}

private fun crteateSpeechRecognizerIntent(): Intent {
    return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Hablar ahora .....")
    }
}

