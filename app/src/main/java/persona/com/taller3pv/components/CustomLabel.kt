package persona.com.taller3pv.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Texto reutilizable para títulos o mensajes dentro de las pantallas.
 */
@Composable
fun CustomLabel(
    text: String,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleLarge
) {
    Text(
        text = text,
        style = style,
        modifier = modifier
    )
}
