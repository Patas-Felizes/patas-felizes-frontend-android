package com.example.patasfelizes.api
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.api.services.*
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
class RetrofitInitializer {
    // Custom Gson adapter for Animal ensuring foto is handled as a Base64 string.
    class AnimalAdapter : JsonSerializer<Animal>, JsonDeserializer<Animal> {
        override fun serialize(src: Animal, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            val jsonObj = JsonObject()
            jsonObj.addProperty("animal_id", src.animal_id)
            jsonObj.addProperty("nome", src.nome)
            jsonObj.addProperty("idade", src.idade)
            jsonObj.addProperty("foto", src.foto) // foto em Base64
            jsonObj.addProperty("descricao", src.descricao)
            jsonObj.addProperty("sexo", src.sexo)
            jsonObj.addProperty("castracao", src.castracao)
            jsonObj.addProperty("status", src.status)
            jsonObj.addProperty("especie", src.especie)
            jsonObj.addProperty("data_cadastro", src.data_cadastro)
            return jsonObj
        }
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Animal {
            val jsonObj = json.asJsonObject
            return Animal(
                animal_id = jsonObj.get("animal_id")?.asInt ?: 0,
                nome = jsonObj.get("nome").asString,
                idade = jsonObj.get("idade").asString,
                foto = jsonObj.get("foto")?.asString ?: "",
                descricao = jsonObj.get("descricao").asString,
                sexo = jsonObj.get("sexo").asString,
                castracao = jsonObj.get("castracao").asString,
                status = jsonObj.get("status").asString,
                especie = jsonObj.get("especie").asString,
                data_cadastro = jsonObj.get("data_cadastro")?.asString ?: LocalDate.now().toString()
            )
        }
    }

    private val customGson: Gson = GsonBuilder()
        .registerTypeAdapter(Animal::class.java, AnimalAdapter())
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.122.24:5000/")
        // Troque por seu IP aqui e em res/xml/network_security_config.xml

        .addConverterFactory(GsonConverterFactory.create(customGson))
        .build()
    fun animalsService() = retrofit.create(AnimalsService::class.java)
    fun adoptersService() = retrofit.create(AdoptersService::class.java)
    fun adoptionsService() = retrofit.create(AdoptionsService::class.java)
    fun voluntaryService() = retrofit.create(VoluntaryService::class.java)
    fun stockService() = retrofit.create(StockService::class.java)
    fun campaignsService() = retrofit.create(CampaignsService::class.java)
    fun supportService() = retrofit.create(SupportService::class.java)
    fun taskService() = retrofit.create(TaskService::class.java)
    fun hostService() = retrofit.create(HostService::class.java)
    fun tempHomeService() = retrofit.create(TempHomeService::class.java)
    fun donationService() = retrofit.create(DonationService::class.java)
    fun extenseService() = retrofit.create(ExtenseService::class.java)
    fun procedureService() = retrofit.create(ProcedureService::class.java)
}