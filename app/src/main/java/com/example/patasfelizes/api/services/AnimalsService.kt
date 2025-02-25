package com.example.patasfelizes.api.services
import com.example.patasfelizes.models.Animal
import retrofit2.Call
import retrofit2.http.*
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.patasfelizes.api.RetrofitInitializer
interface AnimalsService {
    @GET("animals/")
    fun listAnimals(): Call<List<Animal>>
    @GET("/animals/{id}")
    fun getAnimal(@Path("id") id: Int): Call<Animal>
    @POST("/animals/")
    fun createAnimal(@Body animal: Animal): Call<Animal>
    @PUT("/animals/{id}")
    fun updateAnimal(@Path("id") id: Int, @Body animal: Animal): Call<Animal>
    @DELETE("/animals/{id}")
    fun deleteAnimal(@Path("id") id: Int): Call<Void>
    companion object {
        private const val BASE_URL = "http://192.168.0.5:5000"
        fun create(): AnimalsService {
            // Use the custom adapter from RetrofitInitializer to handle the foto field as Base64.
            val gson = GsonBuilder()
                .registerTypeAdapter(Animal::class.java, RetrofitInitializer.AnimalAdapter())
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(AnimalsService::class.java)
        }
    }
}