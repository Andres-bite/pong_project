# Actividades 1, 2 y 3

## Actividad 1. Diagnóstico

| Problema identificado | Ubicación | Consecuencia | Propuesta de solución |
|---|---|---|---|
| Pausa por espera activa con `Thread.yield()` | `BallRunner.run()` | Mientras está pausado el hilo consume CPU sin realizar trabajo útil. | Sustituir el hilo por un `javax.swing.Timer` que se detenga al pausar. |
| Hilos sin ciclo de vida controlado | Constructor y `run()` de `Board` | El hilo de repintado se ejecuta indefinidamente y el método `stop()` de `BallRunner` nunca se invoca. | Eliminar los hilos auxiliares y detener el temporizador al cerrar la ventana. |
| Estado de pausa y terminación no visible entre hilos | Campos `isPaused` y `stop` de `BallRunner` | El hilo de movimiento puede no observar a tiempo un cambio realizado desde el EDT. | Centralizar todos los cambios de estado en el EDT mediante `Timer`. |
| Figuras mutables compartidas entre hilos | `BallRunner`, `Board.paintComponent()` y `Paddle` | El renderizado puede leer posiciones parcialmente actualizadas. | Actualizar y dibujar el estado exclusivamente en el EDT. |
| Repintado solicitado desde un hilo secundario | `Board.run()` | La ejecución no tiene una condición de salida y añade coordinación innecesaria a Swing. | Solicitar el repintado desde la acción del `Timer`, que se ejecuta en el EDT. |
| Colisión con la raqueta omite la espera del ciclo | Rama de `BallRunner.run()` que usa `continue` | Tras una intersección se pueden ejecutar iteraciones aceleradas y producir rebotes erráticos. | Mantener una única actualización por evento de temporizador. |
| `Board` mezcla vista, ciclo de ejecución e interacción | `Board` | La clase tiene responsabilidades que dificultan razonar sobre el estado concurrente. | Dejar en `Board` la vista y la entrada; asignar el ciclo de actualización a `BallRunner`. |

Las responsabilidades originales son: `Main` crea la ventana; `Board` renderiza el tablero y recibe el teclado; `BallRunner` modifica la posición de la pelota; y `Paddle` representa y desplaza la raqueta. Antes del cambio, las figuras y los indicadores de pausa/terminación eran compartidos por el EDT y dos hilos secundarios.

## Actividad 2. Control de ejecución

Se añadió `GameState` con los estados `RUNNING`, `PAUSED` y `STOPPED`. `BallRunner` utiliza un único `javax.swing.Timer` de 150 ms para el ciclo de actualización. `start()` y `resume()` pasan de `PAUSED` a `RUNNING`; `pause()` detiene el temporizador; y `stop()` lo detiene definitivamente y cambia a `STOPPED`.

El temporizador no crea un hilo de sondeo ni continúa programando actualizaciones durante la pausa. La ventana invoca `Board.stop()` durante su cierre, con lo que el temporizador queda detenido antes de que termine la aplicación.

## Actividad 3. Concurrencia

La construcción del tablero se realiza desde `SwingUtilities.invokeLater`. Los eventos de teclado, las acciones de `javax.swing.Timer`, el repintado y el cierre de la ventana se procesan en el Event Dispatch Thread. Por ello, las posiciones de la pelota y la raqueta, así como el estado del juego, se consultan y modifican en un único hilo.

No se emplean campos `volatile` ni bloques `synchronized`, porque no quedan hilos de trabajo que compartan ese estado. `BallRunner` contiene la actualización del estado y solicita un repintado al finalizarla; `Board.paintComponent()` se limita a representar el estado actual y no lo modifica.
