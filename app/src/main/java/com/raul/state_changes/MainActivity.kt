package com.raul.state_changes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raul.state_changes.ui.theme.State_changesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            State_changesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val navController  = rememberNavController()


    // LaunchedEffect(key1 = , block = )

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val vm = viewModel<MainViewModel>()
            val state = vm.state
           // val flow = vm.sharedFlow
            // val lifecycleOwner = LocalLifecycleOwner.current
           /* LaunchedEffect(flow, lifecycleOwner.lifecycle){
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    flow.collect { event ->
                        when(event){
                             NavigationEvent.NavigateToProfile -> {
                                navController.navigate("profile")
                            }
                            else -> println("Should not navigate")
                        }
                    }
                }
            }*/
            LaunchedEffect(state.isLoggedIn){
                if(state.isLoggedIn){
                    navController.navigate("profile")
                    vm.logout()
                }
            }
            LoginScreen(navController){
                vm.login()
            }
        }
        composable("profile") {
            ProfileScreen(navController)
        }

    }
}

@Composable
fun LoginScreen(navController: NavController, onClick: ()-> Unit){
    val vm = viewModel<MainViewModel>()
    val state = vm.state
    Box(contentAlignment = Alignment.Center){
        ElevatedButton(onClick = onClick) {
           if(state.loading){
               CircularProgressIndicator()
           }

               Text("Login")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController){
    Box(contentAlignment = Alignment.Center){

            Text("Welcome!")

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    State_changesTheme {
        Greeting("Android")
    }
}