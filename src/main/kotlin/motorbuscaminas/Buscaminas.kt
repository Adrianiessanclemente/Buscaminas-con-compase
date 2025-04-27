package motorbuscaminas
public class Buscaminas {

    var xTablero: Int = 0
    var yTablero: Int = 0
    var numeroMinas: Int = 0

    private  lateinit var tablero: Array<Array<Celda>>

    val tableroVisible: Array<Array<Celda>>
        get() = Array(yTablero){
            fila->Array(xTablero){
                columna -> tablero[fila][columna].copy()
        }
        }




    fun crearTablero(columnas: Int , filas: Int, minas: Int) {

        require(filas>0 && columnas >0){"El tamaño de las filas y las columnas debe ser mayor que 0"}
        require(minas < filas * columnas) { "El número de minas debe ser menor que el número de celdas" }

         tablero = Array(filas){Array(columnas){ Celda() } }

        xTablero = columnas
        yTablero = filas
        numeroMinas = minas

        colocarMinas();

        calcularMinasCerca();


    }

    private fun colocarMinas() {
       var minasColocadas = 0;

        while(minasColocadas < numeroMinas){
             val fila = (0 until yTablero).random()
            val columnas= (0 until xTablero).random()

            val celda = tablero[fila][columnas]

            if(!celda.tieneMina){
                celda.tieneMina = true;
                minasColocadas++

            }
        }

    }

    private fun calcularMinasCerca() {

        for (fila in 0 until yTablero) {
            for (columna in 0 until xTablero) {

                if (tablero[fila][columna].tieneMina) {
                    continue
                }

                var minasCercanas = 0

                for (i in -1..1) {
                    for (j in -1..1) {

                        if (fila + i in 0 until yTablero && columna + j in 0 until xTablero) {

                            if (tablero[fila + i][columna + j].tieneMina) {
                                minasCercanas++
                            }
                        }
                    }
                }
                tablero[fila][columna].minasCerca = minasCercanas
            }




        }
    }
    fun destapar(fila: Int, columna: Int) {
        val celda = tablero[fila][columna]


        if (celda.estaDestapada || celda.tieneBandera) return


        celda.estaDestapada = true


        if (celda.minasCerca == 0) {
            for (i in -1..1) {
                for (j in -1..1) {
                    if (fila + i in 0 until yTablero && columna + j in 0 until xTablero) {
                        destapar(fila + i, columna + j)
                    }
                }
            }
        }

    }

    fun colocarBandera(fila: Int, columna: Int) {
        val celda = tablero[fila][columna]


        if (!celda.estaDestapada) {
            celda.tieneBandera = !celda.tieneBandera
        }
    }

    fun estaFinalizado(): Boolean {
        for (fila in 0 until yTablero) {
            for (columna in 0 until xTablero) {
                val celda = tablero[fila][columna]

                if (!celda.estaDestapada && !celda.tieneMina) {
                    return false
                }
            }
        }
        return true
    }


}


        data class  Celda(   var tieneMina: Boolean = false,
                             var estaDestapada: Boolean = false,
                             var tieneBandera: Boolean = false,
                             var minasCerca: Int = 0)





