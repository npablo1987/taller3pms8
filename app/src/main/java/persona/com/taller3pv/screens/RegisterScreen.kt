package persona.com.taller3pv.screens

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
 * Pantalla de registro de un nuevo usuario.
 *
 * @param onRegisterClick se llama al presionar "Registrar", entregando el nombre ingresado.
 */
@Composable
fun RegisterScreen(
    onRegisterSuccess: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomLabel(text = "Registro")

        Spacer(modifier = Modifier.height(24.dp))

        CustomInput(
            value = name,
            onValueChange = { name = it },
            label = "Nombre"
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar contraseña"
        )

        if (errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Registrar",
            onClick = {

                if(password != confirmPassword){
                    errorMessage = "Las contraseñas no coinciden"

                }else{
                    errorMessage = ""
                    UserRepository.registerUser(
                        name,
                        email,
                        password,
                        onSuccess = { onRegisterSuccess(name)},
                        onError = { errorMessage = it }
                        )

                }




            }
        )
    }
}
