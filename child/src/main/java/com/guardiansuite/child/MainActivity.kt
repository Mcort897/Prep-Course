package com.guardiansuite.child

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MaterialTheme {
				Surface(modifier = Modifier.fillMaxSize()) {
					ChildHome(onOpenAccessibility = {
						startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
					})
				}
			}
		}
	}
}

@Composable
fun ChildHome(onOpenAccessibility: () -> Unit) {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Guardian Child")
		Button(onClick = onOpenAccessibility) {
			Text("Activar servicio de accesibilidad")
		}
	}
}