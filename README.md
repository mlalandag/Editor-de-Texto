# Editor de Texto
Editor de texto orientado a lineas


Dependencias

Para ejecutar el proyecto es necesario que las siguientes herramientas estén instaladas.

•	Maven
•	Java 1.8 (o superior)

Instalar Maven

El proyecto requiere Maven para ejecutar la compilación. Usar el siguiente enlace para descargar el archivo de instalación de Maven en caso de que no lo esté ya.

https://maven.apache.org/install.html

Asegurarse de que los directorios bin tanto de Java como de Maven se hayan especificados en la variable de entorno PATH.


Compilación

Una vez descargado en un directorio local, navegar hasta el mismo y realizar un Maven Build ejecutando:

mvn clean install

Si el build acaba correctamente se habrá creado un directorio “target” en el que se encontrará el archivo “jar” de la aplicación.

Para arrancar la aplicación navegaremos hasta el mencionado directorio y ejecutaremos el siguiente comando:

java -jar editor-texto-0.0.1-SNAPSHOT.jar


Ejecución desde un IDE

Se puede también ejecutar la aplicación desde el IDE de preferencia (Eclipse, STS, Intellij .. )
importándola en el mismo como un projecto Maven existente y, haciendo click con bo-tón derecho en la clase "EditorTextoApplication", ejecutarla como aplicación java.



