// Animal.kt
package com.example.patasfelizes.models

import java.time.LocalDate
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.google.gson.*

data class Animal(
    val animal_id: Int = 0,
    val nome: String,
    val idade: String,
    val foto: String = "", // foto em string Base64
    val descricao: String,
    val sexo: String,
    val castracao: String,
    val status: String,
    val especie: String,
    val data_cadastro: String = LocalDate.now().toString()
) {
    fun getFotoAsByteArray(): ByteArray {
        return if (foto.isNotEmpty()) {
            try {
                decodeFromBase64(foto)
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao decodificar foto: ${e.message}")
                ByteArray(0)
            }
        } else {
            ByteArray(0)
        }
    }

    fun getFotoAsBitmap(): Bitmap? {
        val bytes = getFotoAsByteArray()
        return if (bytes.isNotEmpty()) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else {
            null
        }
    }

    companion object {
        private const val TAG = "Animal"
        fun encodeToBase64(data: ByteArray): String {
            return Base64.encodeToString(data, Base64.DEFAULT)
        }
        fun decodeFromBase64(data: String): ByteArray {
            return Base64.decode(data, Base64.DEFAULT)
        }
    }
}