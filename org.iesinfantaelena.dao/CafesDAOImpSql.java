package org.iesinfantaelena.dao;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Cafe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Funcionalidad de base de datos en SQL
 */
public class CafesDAOImpSql implements CafesDAO {
	public static final String DB_URL = "jdbc:mysql://localhost:3306/mercado";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "";
	
	private Connection connection = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	
	public CafesDAOImpSql() throws AccesoDatosException {
		try {
			Class.forName("org.h2.Driver");
			
			connection =  DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception ex) {
			throw new AccesoDatosException("No se pudo inicializar la conexión a la base de datos SQL", ex);
		}
	}
	
	@Override
	public void verTabla() throws AccesoDatosException {
		if (createStatement()) {
			try {
				resultSet = statement.executeQuery("SELECT CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL FROM cafes");
				
				for (Cafe cafe : getCafesFromResult()) {
					System.out.println(cafe);
				}
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudieron recuperar los cafés", ex);
			} finally {
				liberar();
			}
		}
		
		throw new AccesoDatosException("No se pudieron recuperar los cafés");
	}
	
	@Override
	public Cafe buscar(String nombre) throws AccesoDatosException {
		if (prepareStatement("SELECT CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL FROM cafes WHERE CAF_NOMBRE = ?")) {
			try {
				preparedStatement.setString(1, nombre);
				
				resultSet = preparedStatement.executeQuery();
				
				List<Cafe> cafes = getCafesFromResult();
				
				if (cafes.size() >= 1) {
					return cafes.get(0);
				} else {
					return null;
				}
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo buscar el café", ex);
			} finally {
				liberar();
			}
		}
		
		throw new AccesoDatosException("No se pudo buscar el café");
	}
	
	@Override
	public Cafe insertar(String nombre, int provid, float precio, int ventas, int total) throws AccesoDatosException {
		Cafe cafe = new Cafe();
		cafe.setNombre(nombre);
		cafe.setProvid(provid);
		cafe.setPrecio(precio);
		cafe.setVentas(ventas);
		cafe.setTotal(total);
		
		if (prepareStatement("INSERT INTO cafes (CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL) VALUES (?, ?, ?, ?, ?)")) {
			try {
				preparedStatement.setString(1, cafe.getNombre());
				preparedStatement.setInt(2, cafe.getProvid());
				preparedStatement.setFloat(3, cafe.getPrecio());
				preparedStatement.setInt(4, cafe.getVentas());
				preparedStatement.setInt(5, cafe.getTotal());
				
				preparedStatement.execute();
				
				return cafe;
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo insertar el café", ex);
			} finally {
				liberar();
			}
		}
		
		throw new AccesoDatosException("No se pudo insertar el café");
	}
	
	@Override
	public void borrar(String nombre) throws AccesoDatosException {
		if (prepareStatement("DELETE FROM cafes WHERE CAF_NOMBRE = ?")) {
			try {
				preparedStatement.execute();
				
				return;
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudo borrar el café", ex);
			} finally {
				liberar();
			}
		}
		
		throw new AccesoDatosException("No se pudo borrar el café");
	}
	
	@Override
	public List<Cafe> cafesPorProveedor(int provid) throws AccesoDatosException {
		List<Cafe> cafes = new ArrayList<>();
		
		if (prepareStatement("SELECT CAF_NOMBRE, PROV_ID, PRECIO, VENTAS, TOTAL FROM cafes WHERE CAF_NOMBRE = ?")) {
			try {
				preparedStatement.setInt(1, provid);
				
				resultSet = preparedStatement.executeQuery();
				
				return getCafesFromResult();
			} catch (SQLException ex) {
				throw new AccesoDatosException("No se pudieron buscar los cafés por proveedor", ex);
			} finally {
				liberar();
			}
		}
		
		throw new AccesoDatosException("No se pudieron buscar los cafés por proveedor");
	}
	
	private List<Cafe> getCafesFromResult() {
		List<Cafe> cafes = new ArrayList<>();
		
		try {
			while (resultSet.next()) {
				Cafe cafe = new Cafe();
				cafe.setNombre(resultSet.getString(1));
				cafe.setProvid(resultSet.getInt(2));
				cafe.setPrecio(resultSet.getFloat(3));
				cafe.setVentas(resultSet.getInt(4));
				cafe.setTotal(resultSet.getInt(5));
				
				cafes.add(cafe);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return cafes;
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
	
	@Override
	public void cerrar() throws AccesoDatosException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ex) {
			throw new AccesoDatosException("No se pudieron buscar los cafés por proveedor");
		}
	}
	
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
}
