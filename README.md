
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

2. Agregue al método 'checkHost' un parámetro entero N, correspondiente al número de hilos entre los que se va a realizar la búsqueda (recuerde tener en cuenta si N es par o impar!). Modifique el código de este método para que divida el espacio de búsqueda entre las N partes indicadas, y paralelice la búsqueda a través de N hilos. Haga que dicha función espere hasta que los N hilos terminen de resolver su respectivo sub-problema, agregue las ocurrencias encontradas por cada hilo a la lista que retorna el método, y entonces calcule (sumando el total de ocurrencuas encontradas por cada hilo) si el número de ocurrencias es mayor o igual a _BLACK_LIST_ALARM_COUNT_. Si se da este caso, al final se DEBE reportar el host como confiable o no confiable, y mostrar el listado con los números de las listas negras respectivas. Para lograr este comportamiento de 'espera' revise el método [join](https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html) del API de concurrencia de Java. Tenga también en cuenta:

	* Dentro del método checkHost Se debe mantener el LOG que informa, antes de retornar el resultado, el número de listas negras revisadas VS. el número de listas negras total (línea 60). Se debe garantizar que dicha información sea verídica bajo el nuevo esquema de procesamiento en paralelo planteado.

	* Se sabe que el HOST 202.24.34.55 está reportado en listas negras de una forma más dispersa, y que el host 212.24.24.55 NO está en ninguna lista negra.


**Parte II.I **

Para minimizar la cantidad de consultas que los threads realizan cuando la condición ya se ha cumplido(condición de carrera), tenemos que la variable donde se reporta las ocurrencias encontradas debe ser la misma para todos los threads, por lo que esta variiable se convierte en una región crítica, así el número de ocurrencias para todos es el mismo, constantemente hay que verificar que si el total de ocurrencias es igual a 5, debemos notificar a los threads que paren la busqueda, por lo que también necesitamos un flag que sea la misma para todos, por lo que también sería una región Crítica de nuestro código.


**Parte III - Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para realizar las validación de direcciones IP dispersas (por ejemplo 202.24.34.55), tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
3. Tantos hilos como el doble de núcleos de procesamiento.
4. 50 hilos.
5. 100 hilos.

Al iniciar el programa ejecute el monitor jVisualVM, y a medida que corran las pruebas, revise y anote el consumo de CPU y de memoria en cada caso. ![](img/jvisualvm.png)

Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):

**Parte IV - Ejercicio Black List Search**

1. Según la [ley de Amdahls](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?):

	![](img/ahmdahls.png), donde _S(n)_ es el mejoramiento teórico del desempeño, _P_ la fracción paralelizable del algoritmo, y _n_ el número de hilos, a mayor _n_, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos?, cómo se compara este desempeño cuando se usan 200?. 

2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste?.

3. De acuerdo con lo anterior, si para este problema en lugar de 100 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 100 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 100/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta.



