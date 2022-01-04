package json

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonSample {

    private val json: Json
        get() = Json {
            isLenient = true
            ignoreUnknownKeys = true
            allowSpecialFloatingPointValues = true
            useArrayPolymorphism = true
            encodeDefaults = false
        }


    fun decodeFromStringTest(){
        val string = "{\"name\": \"Batu Tasvaluan\", \"age\": 32}"
        val dataModel = json.decodeFromString<DataModel>(string)
        println(dataModel)
    }

    fun decodeFromStringTest2(){
        val string = "[{\"name\": \"Batu Tasvaluan\", \"age\": 32} , {\"name\": \"Langui Tasvaluan\", \"age\": 1}]"
        val dataModel = json.decodeFromString<List<DataModel>>(string)
        println(dataModel)
    }
}