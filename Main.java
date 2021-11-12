import org.iesinfantaelena.dao.DAOType;
import org.iesinfantaelena.dao.Cafes;
import org.iesinfantaelena.dao.CafesDAOImpSql;
import org.iesinfantaelena.dao.Libros;
import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Libro;
import org.iesinfantaelena.utils.Utilidades;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws IOException, SQLException, AccesoDatosException {
		fernando();
		alberto();
	}
	
	private static void fernando() {
		try {
			// Ejercicio 3
			Utilidades util = new Utilidades();
			util.getConnection();
			
			// ejercicio 7
			Libros libros = new Libros();
			libros.crearTablaLibros();
			libros.getCamposLibroDefault();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void alberto() {
		try {
			//Cafes cafes = new Cafes(DAOType.SQL);
			Cafes cafes = new Cafes(DAOType.H2);
			
			cafes.insertar("Cafetito", 49, 1.0f, 100,1000);
			cafes.insertar("Cafe tacilla", 49, 2.0f, 100,1000);
			cafes.verTabla();
			cafes.buscar("tacilla");
			cafes.cafesPorProveedor(49);
			cafes.borrar("Cafe tacilla");
			cafes.verTabla();
			
			cafes.cerrar();
		} catch (AccesoDatosException ex) {
			ex.printStackTrace();
		}
		
		try {
			// Ejercicio 4
			Libros libros = new Libros(DAOType.H2);
			libros.crearTablaLibros();
			
			// Ejercicio 5
			Libro libroOs = new Libro(12345,"Sistemas Operativos","Tanembaun","Informática",156,3);
			Libro libroMx = new Libro(12453,"Minix","Stallings","Informática",345,4);
			Libro libroRs = new Libro(1325,"Linux","Richard Stallman","FSF",168,10);
			Libro libroJg = new Libro(1725,"Java","Juan Garcia","Programación",245,9);
			libros.anadirLibro(libroOs);
			libros.anadirLibro(libroMx);
			libros.anadirLibro(libroRs);
			libros.anadirLibro(libroJg);
			
			// Ejercicio 6
			Libro libro = new Libro();
			libro.setISBN(1234);
			libro.setTitulo("La isla misteriosa");
			libro.setAutor("Julio Verne");
			libro.setEditorial("Salvat");
			libro.setPaginas(256);
			libro.setCopias(5000);
			
			libros.anadirLibro(libro);
			
			for (String thisField : libros.getCamposLibro()) {
				// Todos los campos en la misma línea
				System.out.print(thisField + " ");
			}
			
			// Una vez imprimidos los campos, nueva línea
			System.out.println();
			
			System.out.println(libros.obtenerLibro(1234).toString());
			
			libro.setCopias(4000);
			libros.actualizarCopias(libro);
			System.out.println(libros.obtenerLibro(1234).toString());
			
			libros.borrar(libro);
			
			libros.cerrar();
			
		} catch (AccesoDatosException ex) {
			ex.printStackTrace();
		}
	}
}
