package br.com.fiap.inovagab

import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

/** Cliente HTTP simples para comunicação com a API Spring Boot local. */
object ApiClient {
    // No emulador Android, 10.0.2.2 representa o localhost do computador.
    const val BASE_URL = "http://10.0.2.2:8080/api/"

    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())

    fun request(
        method: String,
        path: String,
        token: String? = null,
        body: JSONObject? = null,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        executor.execute {
            try {
                val connection = (URL(BASE_URL + path).openConnection() as HttpURLConnection).apply {
                    requestMethod = method
                    connectTimeout = 10_000
                    readTimeout = 10_000
                    setRequestProperty("Accept", "application/json")
                    if (token != null) setRequestProperty("Authorization", "Bearer $token")
                    if (body != null) {
                        doOutput = true
                        setRequestProperty("Content-Type", "application/json")
                        OutputStreamWriter(outputStream, Charsets.UTF_8).use { it.write(body.toString()) }
                    }
                }
                val status = connection.responseCode
                val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                val response = stream?.bufferedReader()?.use(BufferedReader::readText).orEmpty()
                mainHandler.post {
                    if (status in 200..299) onSuccess(response)
                    else onError(if (response.isBlank()) "Erro HTTP $status" else response)
                }
                connection.disconnect()
            } catch (exception: Exception) {
                mainHandler.post { onError("Não foi possível conectar à API: ${exception.message}") }
            }
        }
    }
}
