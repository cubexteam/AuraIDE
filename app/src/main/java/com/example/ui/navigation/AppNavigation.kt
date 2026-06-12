package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.editor.EditorScreen
import com.example.ui.settings.SettingsScreen
import com.example.ui.terminal.TerminalScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("AuraIDE", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text("Editor") },
                    selected = true,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("editor")
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Terminal") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("terminal")
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        navController.navigate("settings")
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "editor") {
            composable("editor") {
                EditorScreen(
                    drawerState = drawerState,
                    onNavigateToTerminal = { navController.navigate("terminal") }
                )
            }
            composable("terminal") {
                TerminalScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("settings") {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
