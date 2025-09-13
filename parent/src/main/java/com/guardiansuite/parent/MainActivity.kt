package com.guardiansuite.parent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
	private lateinit var firebaseAuth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		FirebaseApp.initializeApp(this)
		firebaseAuth = FirebaseAuth.getInstance()
		setContent {
			MaterialTheme {
				Surface(modifier = Modifier.fillMaxSize()) {
					ParentHome(firebaseAuth)
				}
			}
		}
	}
}

@Composable
fun ParentHome(auth: FirebaseAuth) {
	var status by remember { mutableStateOf("No autenticado") }
	var loading by remember { mutableStateOf(false) }

	LaunchedEffect(auth.currentUser) {
		status = if (auth.currentUser != null) "Autenticado: ${auth.currentUser?.uid}" else "No autenticado"
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Guardian Parent")
		Text(text = status)
		Button(onClick = {
			if (!loading) {
				loading = true
				auth.signInAnonymously()
					.addOnCompleteListener { task ->
						loading = false
						status = if (task.isSuccessful) {
							"Autenticado: ${auth.currentUser?.uid}"
						} else {
							"Error de autenticación"
						}
					}
			}
		}) {
			Text(if (loading) "Autenticando..." else "Autenticarse (anónimo)")
		}
	}
}