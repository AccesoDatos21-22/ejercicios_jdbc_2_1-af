package org.iesinfantaelena.dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Cafe;
import org.iesinfantaelena.modelo.Libro;
import org.iesinfantaelena.utils.Utilidades;


/**
 * @descrition
 * @author Carlos
 * @date 23/10/2021
 * @version 1.0
 * @license GPLv3
 */

public class Libros {
	public static final String DB_URL_H2 = "jdbc:h2:mem:~/libros";
	public static final String DB_URL_SQL = "jdbc:mysql://localhost:3306/libros";
	public static final String DB_USERNAME = "";
	public static final String DB_PASSWORD = "";
	private static final String SELECT_CAMPOS_QUERY = "SELECT * FROM LIBROS LIMIT 1";
	
	// Consultas a realizar en BD
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	
	/**
	 * Constructor: inicializa conexión
	 *
	 * @throws AccesoDatosException
	 */
	public Libros() throws AccesoDatosException {
		try {
			// Obtenemos la conexión
			this.connection = new Utilidades().getConnection();
			this.statement = null;
			this.preparedStatement = null;
			this.resultSet = null;
		} catch (IOException e) {
			// Error al leer propiedades
			// En una aplicación real, escribo en el log y delego
			System.err.println(e.getMessage());
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			// System.err.println(sqle.getMessage());
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
		}
	}
	
	public Libros(DAOType type) throws AccesoDatosException {
		try {
			Class.forName("org.h2.Driver");
			
			if (type == DAOType.H2) {
				connection =  DriverManager.getConnection(DB_URL_H2, DB_USERNAME, DB_PASSWORD);
			} else {
				connection =  DriverManager.getConnection(DB_URL_SQL, DB_USERNAME, DB_PASSWORD);
			}
		} catch (Exception ex) {
			throw new AccesoDatosException("No se pudo inicializar la conexión a la base de datos", ex);
		}
	}
	
	public void crearTablaLibros() throws AccesoDatosException {
		try {
			createStatement();
			
			statement.execute("create table libros ("
					+ "isbn integer not null,"
					+ "titulo varchar(50) not null,"
					+ "autor varchar(50) not null,"
					+ "editorial varchar(25) not null,"
					+ "paginas integer not null,"
					+ "copias integer not null,"
					+ "constraint isbn_pk primary key (isbn)"
					+ ");");
		} catch (Exception ex) {
			throw new AccesoDatosException("No se pudo crear la tabla 'libros'", ex);
		}
	}
	
	private boolean createStatement() {
		try {
			statement = connection.createStatement();
			
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			
			return false;
		}
	}
	
	private boolean prepareStatement(String statement) {
		try {
			preparedStatement = connection.prepareStatement(statement);
			
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			
			return false;
		}
	}
	
	/**
	 * Método para cerrar la conexión
	 *
	 * @throws AccesoDatosException
	 */
	public void cerrar() throws AccesoDatosException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ex) {
			throw new AccesoDatosException("No se pudo cerrar la conexión");
		}
	}
	
	/**
	 * Método para liberar recursos
	 *
	 * @throws AccesoDatosException
	 */
	public void liberar() throws AccesoDatosException {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			
			if (statement != null) {
				statement.close();
			}
		} catch (Exception ex) {
			throw new AccesoDatosException("No se pudo liberar la conexión", ex);
		}
	}
	
	/**
	 * Metodo que muestra por pantalla los datos de la tabla libros
	 *
	 * @throws SQLException
	 */
	public List<Libro> verCatalogo() throws AccesoDatosException {
		if (createStatement()) {
			try {
				resultSet = statement.executeQuery("SELECT isbn, titulo, autor, editorial, paginas, copias FROM libros");
				
				System.out.println(); // Padding
				
				System.out.println("Catálogo de libros:");
				
				for (Libro libro : getLibrosFromResult()) {
					System.out.println(libro.toString());
				}
				
				System.out.println(); // Padding
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudieron cargar los libros", ex);
			} finally {
				liberar();
			}
		}
		
		return null;
	}
	
	/**
	 * Actualiza el numero de copias para un libro
	 * @param libro
	 * @throws AccesoDatosException
	 */
	
	public void actualizarCopias(Libro libro) throws AccesoDatosException {
		if (prepareStatement(
				"UPDATE libros SET " +
						"titulo = ?, " +
						"autor = ?, " +
						"editorial = ?, " +
						"paginas = ?, " +
						"copias = ?" +
						" WHERE isbn = ?")) {
			try {
				preparedStatement.setString(1, libro.getTitulo());
				preparedStatement.setString(2, libro.getAutor());
				preparedStatement.setString(3, libro.getEditorial());
				preparedStatement.setInt(4, libro.getPaginas());
				preparedStatement.setInt(5, libro.getCopias());
				preparedStatement.setInt(6, libro.getISBN());
				
				preparedStatement.execute();
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo actualizar el libro", ex);
			} finally {
				liberar();
			}
		}
	}
	
	/**
	 * Añade un nuevo libro a la BD
	 * @param libro
	 * @throws AccesoDatosException
	 */
	public void anadirLibro(Libro libro) throws AccesoDatosException {
		if (prepareStatement(
				"INSERT INTO libros (isbn, titulo, autor, editorial, paginas, copias) " +
						"VALUES (?, ?, ?, ?, ?, ?);")) {
			try {
				preparedStatement.setInt(1, libro.getISBN());
				preparedStatement.setString(2, libro.getTitulo());
				preparedStatement.setString(3, libro.getAutor());
				preparedStatement.setString(4, libro.getEditorial());
				preparedStatement.setInt(5, libro.getPaginas());
				preparedStatement.setInt(6, libro.getCopias());
				
				preparedStatement.execute();
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo insertar el libro", ex);
			} finally {
				liberar();
			}
		}
	}
	
	/**
	 * Borra un libro por ISBN
	 * @param libro
	 * @throws AccesoDatosException
	 */
	public void borrar(Libro libro) throws AccesoDatosException {
		if (prepareStatement("DELETE FROM libros WHERE isbn = ?;")) {
			try {
				preparedStatement.setInt(1, libro.getISBN());
				
				preparedStatement.execute();
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo borrar el libro", ex);
			} finally {
				liberar();
			}
		}
	}
	
	/**
	 * Devulve los nombres de los campos de BD
	 * @return
	 * @throws AccesoDatosException
	 */
	public String[] getCamposLibro() throws AccesoDatosException {
		String[] fields = new String[64];
		
		if (createStatement()) {
			try {
				resultSet = statement.executeQuery("SELECT * FROM libros;");
				
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				int i;
				
				for (i = 1; i <= columnCount; i++ ) {
					fields[i - 1] = rsmd.getColumnName(i);
				}
				
				fields = Arrays.copyOfRange(fields, 0, i - 1);
				
				return fields;
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudieron recuperar los campos", ex);
			} finally {
				liberar();
			}
		}
		
		return null;
	}
	
	public String[] getCamposLibroDefault() throws AccesoDatosException {
		
		/*Sentencia sql con parámetros de entrada*/
		preparedStatement = null;
		/*Conjunto de Resultados a obtener de la sentencia sql*/
		resultSet= null;
		ResultSetMetaData rsmd = null;
		String[] campos = null;
		try {
			//Solicitamos a la conexion un objeto stmt para nuestra consulta
			preparedStatement = connection.prepareStatement(SELECT_CAMPOS_QUERY);
			
			//Le solicitamos al objeto stmt que ejecute nuestra consulta
			//y nos devuelve los resultados en un objeto ResultSet
			resultSet = preparedStatement.executeQuery();
			rsmd = resultSet.getMetaData();
			int columns = rsmd.getColumnCount();
			campos = new String[columns];
			for (int i = 0; i < columns; i++) {
				//Los indices de las columnas comienzan en 1
				campos[i] = rsmd.getColumnLabel(i + 1);
			}
			return campos;
			
			
		} catch (SQLException sqle) {
			// En una aplicación real, escribo en el log y delego
			Utilidades.printSQLException(sqle);
			throw new AccesoDatosException(
					"Ocurrió un error al acceder a los datos");
			
		} finally{
			liberar();
		}
	}
	
	public Libro obtenerLibro(int ISBN) throws AccesoDatosException {
		if (prepareStatement("SELECT isbn, titulo, autor, editorial, paginas, copias FROM libros WHERE isbn = ?")) {
			try {
				preparedStatement.setInt(1, ISBN);
				
				resultSet = preparedStatement.executeQuery();
				
				List<Libro> libros = getLibrosFromResult();
				
				if (libros.size() >= 1) {
					return libros.get(0);
				} else {
					return null;
				}
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo buscar el libro", ex);
			} finally {
				liberar();
			}
		}
		
		return null;
	}
	
	private List<Libro> getLibrosFromResult() {
		List<Libro> libros = new ArrayList<>();
		
		try {
			while (resultSet.next()) {
				Libro libro = new Libro();
				libro.setISBN(resultSet.getInt(1));
				libro.setTitulo(resultSet.getString(2));
				libro.setAutor(resultSet.getString(3));
				libro.setEditorial(resultSet.getString(4));
				libro.setPaginas(resultSet.getInt(5));
				libro.setCopias(resultSet.getInt(6));
				
				libros.add(libro);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return libros;
	}
}
