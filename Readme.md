## Proyecto de concurrencia

**Proyecto 3 LdP 02-2024**

### Integrantes
- Carlos Cao C.I. 28.655.925
- Erimar Reis C.I. 29.743.464

---

## Estructura del Proyecto

- **Clases Principales**:
  - `Principal`: Configura la simulación, lee archivo de entrada e inicia los hilos.
  - `Usuario`: Hilo que representa a un usuario solicitando servicio.
  - `Rider`: Representa a un conductor con sus atributos.
  - `App`: Monitor central que gestiona riders y asigna servicios.

## Procesos

1. **Proceso de Usuario**:
   - Solicita un rider que coincida con su tipo de servicio.
   - Espera activamente si no hay riders disponibles.
   - Durante la espera, verifica periódicamente si existe un rider mejor (más cercano o de su misma app).
   - Inicia el viaje una vez transcurrido el tiempo de espera.

2. **Gestión de Riders**:
   - Los riders son mantenidos en un arreglo central (`App`).
   - Se marcan como disponibles/no disponibles según su asignación.

## Recursos Críticos y Sincronización

### Recursos Críticos
1. **Arreglo de Riders (`App.Riders`)**:
   - Accedido concurrentemente por múltiples hilos de usuarios.
   - Operaciones críticas:
     - Búsqueda de riders disponibles (`solicitarRider`).
     - Cambio de estado de riders (`setEstado`).
     - Reemplazo de riders durante la espera (`buscarNuevoRider`).

2. **Contador `ridersActual` en `App`**:
   - Gestiona la posición donde se agregan nuevos riders.

### Mecanismos de Sincronización
- **Métodos Sincronizados en `App`**:
  - `solicitarRider()`: Asigna el mejor rider disponible usando exclusión mutua.
  - `buscarNuevoRider()`: Encuentra un rider mejor que el actual durante la espera.
  - La sincronización se logra con `synchronized` en estos métodos.

## Decisiones de Diseño Clave

1. **Modelo de Monitor**:
   - La clase `App` actúa como monitor centralizado para gestionar riders.
   - Simplifica la coordinación entre hilos mediante métodos sincronizados.

2. **Selección de Riders**:
   - Prioridad por tiempo de llegada y aplicación del usuario.
   - Si dos riders tienen igual tiempo, se selecciona el de la misma app.

3. **Gestión de Espera Activa**:
   - Los usuarios verifican cada segundo riders mejores durante la espera.
   - Implementado con `sleep(1000)` en el hilo de usuario.

4. **Liberación de Riders**:
   - Al finalizar un viaje o al cambiar de rider, se marca como disponible.

## Instrucciones de Uso

### Requisitos
- Java 8 o superior.
- Archivo de entrada con formato:
  ```txt
  Número_de_usuarios
  bipbip,X  // X riders para bipbip
  ridery,Y  // Y riders para ridery
  yummy,Z   // Z riders para yummy