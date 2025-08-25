
### Escuela Colombiana de Ingeniería
### Arquitecturas de Software - ARSW 2025-2
## Ejercicio Introducción al paralelismo - Hilos - Caso BlackListSearch
## Integrantes:
### Juan David Martínez Mendez
### Santiago Gualdrón Rincón
  

**Parte I - Introducción a Hilos en Java**

1. De acuerdo con lo revisado en las lecturas, complete las clases CountThread, para que las mismas definan el ciclo de vida de un hilo que imprima por pantalla los números entre A y B.

Para la clase definimos dos variables importantes A y B, las cuales hacen referencia a las entradas de los números, suponiendo que en todos los casos A <= B, realizamos la siguiente implementación:

<img width="697" height="427" alt="image" src="https://github.com/user-attachments/assets/5a850fbe-96ce-4652-9953-799b9d8176a5" />

Dentro del método run implementamos un timer que nos muestre cuánto tiempo le toma al thread completar la tarea además de la implementación solicitada. Al finalizar la tarea notifica la terminación de la misma junto con el tiempo que le tomo realizarla.

3. Complete el método __main__ de la clase CountMainThreads para que:

<img width="697" height="310" alt="image" src="https://github.com/user-attachments/assets/e128319a-1798-459a-9ff0-b94f46207c5f" />

El método "counting" de la clase "CountThread" lo que hace es inicializar las variables del thread y el objeto, también asignamos los intervalos a cada uno de los tres threads, este es el resultado de la ejecución:

<img width="844" height="610" alt="image" src="https://github.com/user-attachments/assets/c159e4cf-af96-4210-81fb-54d7891e7e29" />

Procedemos a cambiar el método de ejecución a run, este es el resultado:

<img width="809" height="584" alt="image" src="https://github.com/user-attachments/assets/21a106fa-17fa-4b3b-8ac1-eaa06f6636a6" />

Como podemos evidenciar en las capturas, hay una clara diferencia entre ejecutar los threads con start y run

start() -> crea un nuevo hilo y lo programa para la ejecución, la ejecución del hilo es de manera concurrente, es decir, el hilo trabaja de forma simultánea con el hilo principal y otros hilos, por eso la salida de números no es ordenada sino "aleatoria" acorde a como trabaje cada thread.

run() -> la tarea se ejecuta desde el hilo que lo llama más NO en un nuevo hilo, por lo que en este caso NO se evidencia concurrencia, por eso la salida es el resultado de cada thread de acuerdo al orden de ejecución.

**Parte II - Ejercicio Black List Search**

1. Cree una clase de tipo Thread que represente el ciclo de vida de un hilo que haga la búsqueda de un segmento del conjunto de servidores disponibles. Agregue a dicha clase un método que permita 'preguntarle' a las instancias del mismo (los hilos) cuantas ocurrencias de servidores maliciosos ha encontrado o encontró.

La nueva clase thread, ayuda a generar los hilos cada vez que el validador necesite comprobar una ip, enviandole los parámetros de inicio y fin en la busqueda, la dirección ip y el conteo de skds

<img width="1317" height="822" alt="image" src="https://github.com/user-attachments/assets/7c7a5173-0a63-42b7-ab78-453b72115c0b" />

de esta forma, se puede retornar la lista "blackListOccurrences" para revisar cuantas ocurrencias de servidores maliciosos ha encontrado

2. Agregue al método 'checkHost' un parámetro entero N, correspondiente al número de hilos entre los que se va a realizar la búsqueda (recuerde tener en cuenta si N es par o impar!). Modifique el código de este método para que divida el espacio de búsqueda entre las N partes indicadas, y paralelice la búsqueda a través de N hilos. Haga que dicha función espere hasta que los N hilos terminen de resolver su respectivo sub-problema, agregue las ocurrencias encontradas por cada hilo a la lista que retorna el método, y entonces calcule (sumando el total de ocurrencuas encontradas por cada hilo) si el número de ocurrencias es mayor o igual a _BLACK_LIST_ALARM_COUNT_. Si se da este caso, al final se DEBE reportar el host como confiable o no confiable, y mostrar el listado con los números de las listas negras respectivas. Para lograr este comportamiento de 'espera' revise el método [join](https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html) del API de concurrencia de Java. Tenga también en cuenta:

Una vez agregado el nuevo parametro "N", se tiene que revisar si es impar o no con el primer ciclo, para enviarle a los hilos los rangos que deben hacer de busqueda; una vez hecho, se puede iniciar cada hilo y se puede buscar cuantas ocurrencias han encontrado:

<img width="1254" height="754" alt="image" src="https://github.com/user-attachments/assets/68d813b9-28b2-4c12-926d-f51f037cc7fd" />

Dentro del método checkHost Se debe mantener el LOG que informa, antes de retornar el resultado, el número de listas negras revisadas VS. el número de listas negras total (línea 60). Se debe garantizar que dicha información sea verídica bajo el nuevo esquema de procesamiento en paralelo planteado.
Se sabe que el HOST 202.24.34.55 está reportado en listas negras de una forma más dispersa, y que el host 212.24.24.55 NO está en ninguna lista negra.

<img width="973" height="330" alt="image" src="https://github.com/user-attachments/assets/fb838c6f-98f2-4487-a046-e7b99134293b" />

Se encontró que el primer y segunda IP no son confiables, mientras que la tercera IP es completamente confiable
<img width="1152" height="441" alt="image" src="https://github.com/user-attachments/assets/f50af8ac-1c07-4d48-aa46-7ef3b9e28864" />



**Parte II.I **

Para minimizar la cantidad de consultas que los threads realizan cuando la condición ya se ha cumplido(condición de carrera), tenemos que la variable donde se reporta las ocurrencias encontradas debe ser la misma para todos los threads, por lo que esta variiable se convierte en una región crítica, así el número de ocurrencias para todos es el mismo, constantemente hay que verificar que si el total de ocurrencias es igual a 5, debemos notificar a los threads que paren la busqueda, por lo que también necesitamos un flag que sea la misma para todos, por lo que también sería una región Crítica de nuestro código.


**Parte III - Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para realizar las validación de direcciones IP dispersas (por ejemplo 202.24.34.55), tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.

<img width="1919" height="754" alt="image" src="https://github.com/user-attachments/assets/b29baa11-e27d-4c48-bdf9-52ade1e977e1" />
<img width="1580" height="860" alt="image" src="https://github.com/user-attachments/assets/db151efb-f06e-401d-b1cd-54f0dcd1762a" />

2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).

el método: "Runtime.getRuntime().availableProcessors()" indica que hay 8 nucleos de procesadores libres

<img width="1919" height="977" alt="image" src="https://github.com/user-attachments/assets/61a9fd05-5b6a-401f-82e1-90fb1753a506" />
<img width="1579" height="870" alt="image" src="https://github.com/user-attachments/assets/41674409-a931-4920-bef3-8dc48b3969c9" />

3. Tantos hilos como el doble de núcleos de procesamiento.

<img width="1917" height="893" alt="image" src="https://github.com/user-attachments/assets/9c703e80-a2ef-4097-b2c7-84864b0a6451" />
<img width="1574" height="866" alt="image" src="https://github.com/user-attachments/assets/b74c0240-8c90-415a-a486-88cc472f526b" />

4. 50 hilos.

<img width="1919" height="812" alt="image" src="https://github.com/user-attachments/assets/3bb3b46f-47d9-483e-b89d-08745cfaa994" />
<img width="1579" height="867" alt="image" src="https://github.com/user-attachments/assets/9028f6a2-aa41-4070-b401-0a1b79d5f45b" />


5. 100 hilos.

<img width="1919" height="990" alt="image" src="https://github.com/user-attachments/assets/6229ef5b-43ec-4ddd-9761-5ffa5e4234dd" />
<img width="1581" height="871" alt="image" src="https://github.com/user-attachments/assets/0945e000-f062-4a6e-b90a-d1d7391f5789" />


Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):

<img width="1919" height="660" alt="image" src="https://github.com/user-attachments/assets/9ade6c1c-85c3-45c7-b6cc-7d995e36c289" />

Teniendo en cuenta la imagen anterior, gracias a generadores de gráficas aproximadas, se puede mostrar que para reducir el tiempo de ejecución, se pueden generar >45 hilos para un tiempo de <10 segundos, considerando que a partir de esta cantidad, solamente se van a estar reduciendo una cantidad minima de segundos o milisegundos, por lo que lo recomendable es dejarlo <100 hilos para un tiempo de ejecución rapida, y una cantidad de entre 10<x<45 hilos para un balance entre estas 2 variables. 

**Parte IV - Ejercicio Black List Search**

1. Aunque parezca que a mayor cantidad de hilos, más rápido será, realmente no es así, pongamos un ejemplo donde P = 0.9 y n = 200 y 500:
S(200) = 9,56937799
S(500) = 9,823182711
De acuerdo a los resultados, la parte (1-P) es un límite teórico, va a haber un punto donde al aumentar n, no va a producir mejoras siginificativas como vemos en este caso.


2. Caso usar tantos hilos como núcleos: si tenemos la misma cantidad de núcleos e hilos, permite que cada núcleo trabaje en paralelo, por lo que es un punto óptimo.

Caso de usar el doble de hilos: No necesariamente mejora el rendimiento, puede causar "contención" entre los hilos, lo que ralentiza la ejecución porque el SO tiene que gestionar más tareas que puede ejecutar en paralelo.

3. Caso 100 hilos en una CPU: agregar mas hilos no mejora ya que la CPU tiene un número limitado de núcleos, puede llegar el caso donde tengamos overhead y saturación de memoria, causando que el rendimiento real sea menor al teórico.

Caso 1 hilo en cada una de 100 maquinas hipotéticas: Reduce los problemas de context switching, pero al distribuir entre maquinas entra un nuevo overhead de comunicación y sincronización entre nodos, lo cual no se explica en la fórmula original, pero en la práctica estos factores hacen que el speedup sea menor al teórico.

Caso c hilos en 100/c hilos: Este caso es un balance, donde se aprovecha cada máquina(usando todos los núcleos disponibles), hay menos overhead y menor comunicación entre nodos.



