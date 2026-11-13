# Pong — Java Swing y Java 2D

Juego Pong para un jugador desarrollado con Java Swing. La pelota rebota en las paredes laterales, el límite superior y la raqueta. Si supera el borde inferior, la partida termina.

## Requisitos

- JDK compatible con Gradle 9.8.2
- Entorno gráfico de escritorio para ejecutar la interfaz

## Compilar y probar

```bash
bash gradlew test
```

## Ejecutar

```bash
bash gradlew classes
java -cp build/classes/java/main mx.unison.pong.Main
```

## Controles

| Tecla | Acción |
|---|---|
| Flecha izquierda | Mover la raqueta a la izquierda |
| Flecha derecha | Mover la raqueta a la derecha |
| Espacio | Pausar |
| R | Reanudar |

## Diseño

- `GameModel`, `Ball` y `Paddle`: estado del juego.
- `GameEngine`: movimiento y detección de colisiones.
- `GameLoop`: ciclo de actualización con `javax.swing.Timer`.
- `Board`: renderizado con Swing y Java 2D.
- `GameInputController`: controles mediante Key Bindings.

La actualización, la entrada y el renderizado se realizan en el Event Dispatch Thread. El temporizador se detiene al pausar, al terminar el juego y al cerrar la ventana.

Los reportes de la práctica están en [`docs/`](docs/).
