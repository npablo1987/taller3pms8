package persona.com.taller3pv.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import persona.com.taller3pv.components.CustomButton
import persona.com.taller3pv.components.CustomInput
import persona.com.taller3pv.components.CustomLabel
import persona.com.taller3pv.components.PasswordInput
import persona.com.taller3pv.data.UserRepository

/**
 * Pantalla de inicio de sesión.
 *
 * @param onLoginClick se llama cuando el usuario presiona "Iniciar sesión", entregando el email ingresado.
 * @param onRegisterClick se llama cuando el usuario presiona el enlace "Registrarse".
 * @param onRecoverPasswordClick se llama cuando el usuario presiona el enlace "Recuperar contraseña".
 */
@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onRecoverPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomLabel(text = "Iniciar sesión")

        Spacer(modifier = Modifier.height(24.dp))

        CustomInput(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña"
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        CustomButton(
            text = "Iniciar sesión",
            onClick = {
                errorMessage = ""

                UserRepository.loginUser(
                    email = email,
                    password = password,
                    onSuccess = { name -> onLoginSuccess(name) },
                    onError = { errorMessage = it }
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿No tienes cuenta? Regístrate",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .clickable { onRegisterClick() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "¿Olvidaste tu contraseña? Recupérala",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .clickable { onRecoverPasswordClick() }
        )
    }
}
