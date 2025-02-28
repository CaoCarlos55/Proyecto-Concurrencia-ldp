## Proyecto de concurrencia

**Proyecto 3 LdP 02-2024**

### Integrantes
- Carlos Cao C.I. 28.655.925
- Erimar Reis C.I. 29.743.464

---

## Estructura del Proyecto: Clases
  - **Principal**: Configura la simulación, lee archivo de entrada e inicia los hilos. Se encarga de leer los parámetros iniciales, genera riders y usuarios de forma aleatoria, respetando las restricciones del enunciado, inicia los hilos de los usuarios y gestiona su ejecución. 

  - **Usuario**: Hilo que representa a un usuario solicitando servicio. Solicita un rider al monitor según su tipo de servicio (carro/mota), espera activamente durante el tiempo de llegada del rider, verificando periodicamente si existe uno mejor. Inicia el viaje y lo completa, liberando al rider al finalizar. 

  - **Rider**: Representa a un conductor con sus atributos.
  El rider tiene un atributo booleano tipo flag que nos ayuda a determinar si está disponible o no. Cada rider es un recurso compartido y crítico, ya que múltiples usuarios compiten por ellos.

  - **App**: Monitor central que gestiona riders y asigna servicios. Se encarga de mantener la lista de riders según el estado en que se encuentra, implementa toda la lógica de prioridad (según el tiempo de llegada y app preferida) y sincroniza el acceso concurrente a los riders para evitar condiciones de carrera. 

## Procesos

1. **Proceso de Usuario**:
   Este se encarga de solicitar un rider que coincida con su tipo de servicio y espera activamente si no hay riders disponibles. Durante la espera, verifica periódicamente si existe un rider mejor (más cercano o de su misma app). Una vez finaliza el tiempo de espera del rider escogido este proceso inicia el viaje.

2. **Gestión de Riders**:
   Los riders son mantenidos en un arreglo central en el monitor `App`, para mejorar su gestión cada rider se marca como disponibles ó no disponibles según su estado de asignación.

## Recursos Críticos y Sincronización

### Recurso Crítico
**Arreglo de Riders (`App.Riders`)**:
   Es el recurso principal de la ejecución ya que es accedido concurrentemente por múltiples hilos de usuarios. 
   De no estar sincronizado, dos usuarios podrían recibir el mismo rider ó un rider se marca como no disponible pero podría seguir apareciendo en las búsquedas de otros usuarios.
   
   Se realizan operaciones críticas como:
     - Búsqueda de riders disponibles (`solicitarRider`).
     - Reemplazo de riders durante la espera (`buscarNuevoRider`).

### Mecanismos de Sincronización
La sincronización se logró mediante la marca `synchronized` en estos métodos, lo cual significa que solo un hilo a la vez puede ejecutar ese método sobre una instancia particular de la clase. Esto nos fue de utilidad para prevenir problemas como las condiciones de carrera, donde múltiples hilos usuario solicitan riders o buscan uno nuevo modificando datos compartidos simultáneamente de una manera que puede causar inconsistencias. Las funciones en las cuales se aplicó este mecanismo fueron:

  - `solicitarRider()`: Asigna el mejor rider disponible usando exclusión mutua.
  - `buscarNuevoRider()`: Encuentra un rider mejor que el actual durante la espera.

## Decisiones de Diseño Clave

1. **Modelo de Monitor**:
   La clase `App` actúa como monitor centralizado para gestionar riders, esta clase simplifica la coordinación entre hilos mediante métodos sincronizados.

2. **Gestión de Espera Activa**:
   Para garantizar que los usuarios escojan un rider mejor estos  durante la espera, verifican cada segundo si hay riders con menos tiempo de llegada o de la misma app. Fue implementado con `sleep(1000)` en el hilo de usuario.

3. **Liberación de Riders**:
   Para garantizar que los riders hagan más de un viaje en toda la ejecución estos deben "liberarse" una vez finaliza el viaje. Para ello al finalizar un viaje o al cambiar de rider, se marca como disponible mediante la flag booleana `disponible` del objeto.

### Consideraciones

1. Al iniciar la ejecución el usuario buscará el mejor rider disponible para él, en terminos de distancia y app solicitada. En una ejecución con más riders que usuarios es probable que no se observen cambios de rider. Se podrá observar cambios de riders en las simulaciones que posean más usuarios que riders, así se producirá el cambio al momento de que se libere alguno mejor. 

1. Si la totalidad de riders poseen el mismo tipo de vehiculo (por ejemplo motocycle) y un usuario solicita el tipo contrario (car), antes de solicitar el vehiculo al monitor se hará el cambio del tipo a solicitar en el usuario.

2. Para mejorar la toma de decisiones los riders poseen un booleano `disponible` que indica si el rider es tomado por un usuario.

3. Luego de instanciado el arreglo de riders no se hacen modificionaciones como añadir o eliminar elementos, solo se modifican los atributos de cada uno según el caso. Esto garantiza que ningún hilo realice cambios catatróficos en el recurso crítico

## Instrucciones de Uso

### Requisitos
- Java 8 o superior.
- El Archivo de entrada de la simulación debe tener un formato estricto:
  ```txt
  Número_de_usuarios
  bipbip,X  // X riders para bipbip
  ridery,Y  // Y riders para ridery
  yummy,Z   // Z riders para yummy

### Modo de Uso
1. Se compila el archivo mediante el uso de makefile 
   ```bash
      makeall
2. Para ejecutar los casos de prueba llamados pN.txt (donde 0<N<5) de hace uso del siguiente comando, cambiando p1.txt por el archivo que se desee probar:
   ```bash
      java Principal "Casos de Prueba\p1.txt"

### Referencias Consultadas

1. Buffereadreader:
- https://dcodingames.com/como-usar-la-clase-bufferedreader/
- https://www.geeksforgeeks.org/java-io-bufferedreader-class-java/
Utilizadas para entender como usar Buffereadreader y leer el archivo.


3. Archivos en Java
- https://www.w3schools.com/java/java_files_read.asp
Utilizado para implementar la lectura de archivos.

---