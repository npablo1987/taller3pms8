package persona.com.taller3pv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import persona.com.taller3pv.screens.HomeScreen
import persona.com.taller3pv.screens.LoginScreen
import persona.com.taller3pv.screens.RecoverPasswordScreen
import persona.com.taller3pv.screens.RegisterScreen

/**
 * Nombres de las rutas usadas por la aplicación.
 */
object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val RECOVER_PASSWORD = "recover_password"
    const val HOME = "home/{userName}"

    fun home(userName: String) = "home/$userName"
}

/**
 * Define el grafo de navegación de la aplicación usando Navigation Compose.
 */
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { email ->
                    navController.navigate(Routes.home(email))
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                },
                onRecoverPasswordClick = {
                    navController.navigate(Routes.RECOVER_PASSWORD)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { name ->
                    navController.navigate(Routes.home(name))
                }
            )
        }

        composable(Routes.RECOVER_PASSWORD) {
            RecoverPasswordScreen(
                onRecoverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.HOME,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            HomeScreen(
                userName = userName,
                onLogoutClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}
