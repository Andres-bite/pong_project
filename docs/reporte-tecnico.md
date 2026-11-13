# Reporte técnico de mejoras

## Cambios realizados

La versión inicial usaba un hilo para mover la pelota y otro para solicitar repintados. Se sustituyeron por un único `javax.swing.Timer`. Se incorporó una máquina de estados con `RUNNING`, `PAUSED`, `STOPPED` y `GAME_OVER`. La lógica de movimiento dejó de depender de formas gráficas mutables y ahora trabaja con un modelo de posiciones y velocidades numéricas.

La estructura separa el modelo, el motor de juego, el ciclo de ejecución, el renderizado y la entrada. La vista no cambia el estado durante `paintComponent()`. Los controles se trasladaron de `KeyListener` a Key Bindings y la raqueta se limita mediante el ancho real del área de juego.

## Decisiones y justificación

`javax.swing.Timer` ejecuta sus acciones en el Event Dispatch Thread. Dado que la entrada, las actualizaciones y el renderizado usan el mismo hilo, no hay accesos simultáneos al modelo y no se requieren bloqueos ni campos `volatile`. Detener el temporizador durante la pausa evita la espera activa y detenerlo al cerrar la ventana libera el ciclo de animación.

La detección de la raqueta considera el borde inferior actual y siguiente de la pelota, además del intervalo horizontal recorrido. Esto reduce la posibilidad de atravesar la raqueta por una actualización discreta. El límite inferior se define como pérdida de pelota para diferenciarlo de las paredes que sí producen rebote.

## Comparación con la versión inicial

| Aspecto | Versión inicial | Versión mejorada |
|---|---|---|
| Ejecución | Dos hilos y ciclos infinitos. | Un temporizador controlado. |
| Pausa | `Thread.yield()` en sondeo activo. | Temporizador detenido. |
| Estado compartido | Accedido desde varios hilos. | Accedido en el EDT. |
| Colisiones | Incrementos grandes y comprobaciones de la figura actual. | Velocidades independientes y cruce de límites. |
| Entrada | `KeyListener` dependiente del foco. | Key Bindings de ventana enfocada. |
| Fin de juego | Rebote en límite inferior. | Estado `GAME_OVER`. |

## Limitaciones

El juego conserva un solo jugador, no incorpora marcador ni reinicio después de `GAME_OVER`. Estas extensiones pueden añadirse sobre el modelo y el motor sin mezclar la lógica con el renderizado.

## Respuestas de reflexión

Modificar un componente Swing fuera del EDT puede provocar estados visuales inconsistentes y errores difíciles de reproducir. `Thread.yield()` no bloquea ni coordina la ejecución y por ello no implementa una pausa eficiente. `javax.swing.Timer` simplifica el uso seguro de Swing, aunque comparte el EDT con la interfaz y no es adecuado para cálculos pesados.

Un desplazamiento grande reduce la precisión de las colisiones cuando solo se revisa la posición final; por eso el motor comprueba el cruce entre posiciones. Separar la lógica de la representación permite probarla sin ventana y facilita añadir funciones. Las pruebas automatizadas detectan regresiones durante la refactorización. El diseño inicial dificulta agregar jugadores, puntuación o niveles porque concentraba modelo, ciclo y vista en pocas clases acopladas.
