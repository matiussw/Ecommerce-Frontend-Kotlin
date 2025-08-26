package com.ecommerce.ecommerceapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommerce.ecommerceapp.ui.theme.Success
import com.ecommerce.ecommerceapp.ui.theme.SuccessDark
import com.ecommerce.ecommerceapp.ui.theme.SuccessMedium
import com.ecommerce.ecommerceapp.ui.theme.TextSecondary
import com.ecommerce.ecommerceapp.utils.SessionManager
import com.ecommerce.ecommerceapp.viewmodels.WelcomeViewModel

@Composable
fun WelcomeScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToUserManagement: () -> Unit,
    onNavigateToLocationManagement: () -> Unit,

    // Parametros Para Category and Products :
    onNavigateToCategoryManagement: () -> Unit,
    onNavigateToProductManagement: () -> Unit,

    viewModel: WelcomeViewModel = viewModel { WelcomeViewModel(sessionManager) }
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E8),
                        Color(0xFFF1F8E9)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Icono de éxito
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = Success
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Mensaje de bienvenida
            Text(
                text = if (viewModel.isAdmin) "¡Bienvenido Admin!" else "¡Bienvenido!",
                style = MaterialTheme.typography.headlineMedium,
                color = SuccessDark,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del usuario
            Text(
                text = viewModel.userName,
                style = MaterialTheme.typography.titleLarge,
                color = SuccessMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tipo de usuario
            Text(
                text = if (viewModel.isAdmin) "👑 Administrador" else "👤 Usuario Regular",
                style = MaterialTheme.typography.titleMedium,
                color = if (viewModel.isAdmin) Color(0xFFFF9800) else TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Card con información de sesión
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Estado de la sesión",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "✓ Sesión iniciada correctamente",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Success
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = viewModel.loginTime,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Opciones disponibles para todos los usuarios
            Text(
                text = "Opciones Disponibles",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Mi Perfil
            ActionButton(
                text = "Mi Perfil",
                icon = Icons.Default.Person,
                description = "Editar datos personales y cambiar contraseña",
                onClick = onNavigateToProfile
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opciones solo para administradores
            if (viewModel.isAdmin) {
                Text(
                    text = "Panel de Administración",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gestión de Usuarios
                ActionButton(
                    text = "Gestión de Usuarios",
                    icon = Icons.Default.AccountCircle,
                    description = "Ver y administrar usuarios del sistema",
                    onClick = onNavigateToUserManagement
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gestión de Ubicaciones
                ActionButton(
                    text = "Gestión de Ubicaciones",
                    icon = Icons.Default.LocationOn,
                    description = "Administrar países, estados y ciudades",
                    onClick = onNavigateToLocationManagement
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gestión de Categorías
                ActionButton(
                    text = "Gestión de Categorías",
                    icon = Icons.Default.List,
                    description = "Crear, editar y eliminar categorías de productos",
                    onClick = onNavigateToCategoryManagement
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gestión de Productos
                ActionButton(
                    text = "Gestión de Productos",
                    icon = Icons.Default.ShoppingCart,
                    description = "Administrar productos, precios, stock e imágenes",
                    onClick = onNavigateToProductManagement
                )

                Spacer(modifier = Modifier.height(32.dp))

            }

            // Botón Cerrar Sesión
            OutlinedButton(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            elevation = null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}