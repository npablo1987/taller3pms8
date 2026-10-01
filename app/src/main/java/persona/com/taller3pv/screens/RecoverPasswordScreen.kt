package persona.com.taller3pv.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import persona.com.taller3pv.components.CustomButton
import persona.com.taller3pv.components.CustomInput
import persona.com.taller3pv.components.CustomLabel

/**
 * Pantalla para recuperar la contraseña ingresando el email.
 *
 * @param onRecoverClick se llama al presionar el botón "Recuperar contraseña".
 */
@Composable
fun RecoverPasswordScreen(
    onRecoverClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomLabel(text = "Recuperar contraseña")

        Spacer(modifier = Modifier.height(24.dp))

        CustomInput(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Recuperar contraseña",
            onClick = onRecoverClick
        )
    }
}
