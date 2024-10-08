package com.weave.a3days

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.weave.design_system.DaysTheme
import com.weave.utils.AppSignatureHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        Log.i("HASH", AppSignatureHelper(this).appSignatures.toString())

        setContent {
            DaysTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DaysTheme.colors.bgDefault
                ) {
                    val navController = rememberNavController()
                    DaysNavGraph(navController = navController)
                }
            }
        }
    }
}
