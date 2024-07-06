package org.deiverbum.app.core.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "mainScreen") {
        //composable("profile") { Profile(onNavigateToFriendsList = { navController.navigate("friendslist") }) }
        //composable("friendslist") { FriendsList(onNavigateToProfile = { navController.navigate("profile") }) }
    }
}