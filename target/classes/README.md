Ejercicio 2:
Llamo a liberar() tras cada operación para liberar los statements y resultSets usados que ya no necesito.
A cerrar() lo llamo al finl del ejercicio, ya que necesito la misma conexión para completar todas los operaciones.
 -Alberto

Ejercicio 7:
Al implementar este metodo en la clase Libros vemos que el metodo
hace una peticion SELECT sobre la tabla LIBROS mediante la conexion levantada por la clase Utilidades();
De la respuesta de dicha sentencia SELECT nos interesa saber el numero de columnas
que tiene la tabala LIBROS mediante getColumnCount().
Tras esto guardamos en el array el nombre de las diversas
columnas de la tabla LIBROS.
 -Fernando
