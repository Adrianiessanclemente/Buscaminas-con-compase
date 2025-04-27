import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import motorbuscaminas.EstadoJuego

@Composable
fun Juego(buscaminas: EstadoJuego) {
    LaunchedEffect(Unit){
        while(true){
            buscaminas.tiempo = buscaminas.obtenerHoraActual()
            delay(1000L)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        BasicText("Tiempo: ${buscaminas.tiempo} segundos")
        Spacer(modifier = Modifier.height(20.dp))



        for (fila in 0 until 8) {
            Row(horizontalArrangement = Arrangement.Center) {
                for (columna in 0 until 8) {
                    Button(onClick = {
                        if (!buscaminas.tableroVisible[fila][columna].tieneBandera) {
                            buscaminas.destapar(fila, columna)
                        }
                    }
                        , modifier = Modifier.padding(2.dp)
                            .pointerInput(Unit){
                            awaitPointerEventScope {
                                while(true){
                                    val event = awaitPointerEvent()
                                    if(event.type == PointerEventType.Press){
                                        val button = event.buttons
                                        if(button.isSecondaryPressed){
                                            buscaminas.colocarBandera(fila,columna)
                                        }
                                    }
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = if(buscaminas.tableroVisible[fila][columna].estaDestapada){
                            Color.LightGray
                        }else{
                            Color.Gray
                        })


                    ) {
                        val celda = buscaminas.tableroVisible[fila][columna]
                        if (celda.estaDestapada || buscaminas.juegoTerminado) {
                            if (celda.tieneMina) {
                                Text("\uD83D\uDCA3")
                            } else  {
                                val minasCerca = celda.minasCerca

                                if(minasCerca>0){
                                    Text("$minasCerca")
                                }else{
                                    Text(" ")
                                }

                            }
                        } else{

                            if(celda.tieneBandera){
                                Text("\uD83D\uDEA9")
                            }else
                            {
                                Text(" ")
                            }

                        }


                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            buscaminas.comenzarPartida()
        }) {
            Text("Nueva Partida")
        }

        if (buscaminas.juegoTerminado) {
            Text(
                text = "¡Has perdido!",
                color = Color.Red

            )
        }

        if (buscaminas.juegoGanado) {
            Text(
                text = "¡Has ganado!",
                color = Color.Green
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val estadoJuego = remember { EstadoJuego() }
        Juego(estadoJuego)
    }
}
