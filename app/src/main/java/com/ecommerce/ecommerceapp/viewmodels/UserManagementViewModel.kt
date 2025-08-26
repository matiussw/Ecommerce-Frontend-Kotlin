package com.ecommerce.ecommerceapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.ecommerceapp.models.*
import com.ecommerce.ecommerceapp.network.ApiClient
import com.ecommerce.ecommerceapp.utils.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserManagementViewModel(private val sessionManager: SessionManager) : ViewModel() {

    var users by mutableStateOf<List<UserProfile>>(emptyList())
    var currentPage by mutableStateOf(1)
    var totalPages by mutableStateOf(1)
    var searchQuery by mutableStateOf("")
    var selectedUser by mutableStateOf<UserProfile?>(null)
    var availableRoles by mutableStateOf<List<Role>>(emptyList())

    // Estados de carga
    var isLoadingUsers by mutableStateOf(false)
    var isLoadingRoles by mutableStateOf(false)
    var isUpdatingRole by mutableStateOf(false)
    var isDeletingUser by mutableStateOf(false)

    // Estados de error y éxito
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)

    init {
        loadUsers()
        loadRoles()
    }

    fun loadUsers(page: Int = 1, search: String = "") {
        isLoadingUsers = true
        viewModelScope.launch {
            try {
                val token = sessionManager.token.first()
                if (token != null) {
                    val response = ApiClient.apiService.getUsers(
                        token = "Bearer $token",
                        page = page,
                        perPage = 10,
                        search = search.ifBlank { null }
                    )

                    if (response.isSuccessful && response.body() != null) {
                        val usersResponse = response.body()!!
                        users = usersResponse.users
                        currentPage = page
                        totalPages = usersResponse.pagination?.total_pages ?: 1
                    } else {
                        errorMessage = "Error al cargar usuarios"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                Log.e("USER_MGMT_DEBUG", "Error loading users", e)
            } finally {
                isLoadingUsers = false
            }
        }
    }

    private fun loadRoles() {
        isLoadingRoles = true
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getRoles()
                if (response.isSuccessful && response.body() != null) {
                    availableRoles = response.body()!!
                }
            } catch (e: Exception) {
                Log.e("USER_MGMT_DEBUG", "Error loading roles", e)
            } finally {
                isLoadingRoles = false
            }
        }
    }

    fun searchUsers(query: String) {
        searchQuery = query
        loadUsers(page = 1, search = query)
    }

    fun loadPage(page: Int) {
        loadUsers(page = page, search = searchQuery)
    }

    fun selectUser(user: UserProfile) {
        selectedUser = user
    }

    fun updateUserRole(userId: Int, roleId: Int) {
        isUpdatingRole = true
        viewModelScope.launch {
            try {
                val token = sessionManager.token.first()
                if (token != null) {
                    val roleRequest = UpdateUserRoleRequest(role_ids = listOf(roleId))
                    val response = ApiClient.apiService.updateUserRoles(
                        token = "Bearer $token",
                        id = userId,
                        roleRequest = roleRequest
                    )

                    if (response.isSuccessful) {
                        successMessage = "Rol de usuario actualizado exitosamente"
                        loadUsers(currentPage, searchQuery) // Recargar lista
                    } else {
                        errorMessage = "Error al actualizar el rol del usuario"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                Log.e("USER_MGMT_DEBUG", "Error updating user role", e)
            } finally {
                isUpdatingRole = false
            }
        }
    }

    fun deleteUser(userId: Int) {
        isDeletingUser = true
        viewModelScope.launch {
            try {
                val token = sessionManager.token.first()
                if (token != null) {
                    val response = ApiClient.apiService.deleteUser("Bearer $token", userId)

                    if (response.isSuccessful) {
                        successMessage = "Usuario eliminado exitosamente"
                        loadUsers(currentPage, searchQuery) // Recargar lista
                    } else {
                        errorMessage = "Error al eliminar el usuario"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                Log.e("USER_MGMT_DEBUG", "Error deleting user", e)
            } finally {
                isDeletingUser = false
            }
        }
    }

    fun clearMessages() {
        errorMessage = null
        successMessage = null
    }
}