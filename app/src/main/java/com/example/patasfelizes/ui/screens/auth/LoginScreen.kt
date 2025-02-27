package com.example.patasfelizes.ui.screens.auth

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.patasfelizes.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Verificar se o usuário já está logado ao iniciar a tela
    LaunchedEffect(Unit) {
        Log.d(TAG, "LaunchedEffect: Verificando se o usuário já está logado")
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            Log.d(TAG, "LaunchedEffect: Usuário já está logado: ${account.email}")
            // Navegar diretamente para a tela de pets
            navController.navigate("pets") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            Log.d(TAG, "LaunchedEffect: Nenhum usuário logado anteriormente")
        }
    }

    // Configurar Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("45166261613-78o31u14q7s359ktpjoaia09h68vcerf.apps.googleusercontent.com")
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    // Criar launcher para o resultado da autenticação
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d(TAG, "Resultado do login recebido: código=${result.resultCode}, data=${result.data != null}")
        // Navegar para pets
        Log.d(TAG, "Navegando para 'pets'")
        navController.navigate("pets") {
            popUpTo("login") { inclusive = true }
        }
        // O resultado da Intent do Google Sign-In não usa RESULT_OK/RESULT_CANCELED da mesma forma
        // Vamos tentar processar o resultado independentemente do resultCode
        try {
            // O importante é que tenhamos data válido
            if (result.data != null) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)

                // Se chegamos aqui, a autenticação foi bem-sucedida
                Log.d(TAG, "Login bem-sucedido: ${account.email}")
                Log.d(TAG, "ID Token: ${account.idToken?.take(20)}...")

                // Vamos verificar se realmente temos a conta após o login
                val accountCheck = GoogleSignIn.getLastSignedInAccount(context)
                if (accountCheck != null) {
                    Log.d(TAG, "Conta verificada: ${accountCheck.email}")

                    // Feedback visual
                    Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

                    // Navegar para pets
                    Log.d(TAG, "Navegando para 'pets'")
                    navController.navigate("pets") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    Log.e(TAG, "Conta não encontrada após login bem-sucedido")
                    Toast.makeText(context, "Erro: Não foi possível confirmar o login", Toast.LENGTH_LONG).show()
                }
            } else {
                Log.e(TAG, "Resultado sem dados (data=null)")
                Toast.makeText(context, "Login cancelado", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.e(TAG, "Erro na API do Google: ${e.statusCode}", e)
//            Toast.makeText(context, "Erro no login: ${e.statusCode}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e(TAG, "Erro inesperado no login", e)
//            Toast.makeText(context, "Erro inesperado: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.defaul_patas),
            contentDescription = "Logo Patas Felizes",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            Log.d(TAG, "Botão de login clicado, iniciando processo de login")

            // Primeiro fazer logout para garantir um login limpo
            coroutineScope.launch {
                try {
                    googleSignInClient.signOut().await()
                    Log.d(TAG, "Logout realizado com sucesso")
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao fazer logout", e)
                }

                // Iniciar processo de login
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }
        }) {
            Text("Login com o Google")
        }

        // Botão adicional para teste direto de navegação
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            Log.d(TAG, "Botão de teste clicado, navegando diretamente para 'pets'")
//            navController.navigate("pets") {
//                popUpTo("login") { inclusive = true }
//            }
//        }) {
//            Text("Testar Navegação Direta")
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}