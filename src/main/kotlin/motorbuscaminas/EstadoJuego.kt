package motorbuscaminas

import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EstadoJuego {
    private val buscaminas = Buscaminas()
    var tiempo by mutableStateOf(obtenerHoraActual())
    var juegoTerminado by mutableStateOf(false)
    var juegoGanado by mutableStateOf(false)
    var tableroVisible by mutableStateOf(Array(8) { Array(8) { motorbuscaminas.Celda() } })

    init {
        buscaminas.crearTablero(8, 8, 10)
        tableroVisible = buscaminas.tableroVisible
    }


    fun destapar(fila: Int, columna: Int) {
        if (!juegoTerminado) {
            buscaminas.destapar(fila, columna)
            tableroVisible = buscaminas.tableroVisible

            if (tableroVisible[fila][columna].tieneMina) {
                juegoTerminado = true
            }
        }
    }

    fun colocarBandera(fila: Int, columna: Int) {
        if (!juegoTerminado) {
            buscaminas.colocarBandera(fila, columna)
            tableroVisible = buscaminas.tableroVisible
        }
    }

    fun comenzarPartida() {

        juegoTerminado = false
        buscaminas.crearTablero(8, 8, 10)
        tableroVisible = buscaminas.tableroVisible
    }

    fun obtenerHoraActual(): String {
        val formato = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formato.format(Date())
    }

    fun verificarVictoria() {

        for (fila in 0 until 8) {
            for (columna in 0 until 8) {
                val celda = buscaminas.tableroVisible[fila][columna]
                if (!celda.tieneMina && !celda.estaDestapada) {
                    return
                }
            }
        }
       juegoGanado = true
    }
}
