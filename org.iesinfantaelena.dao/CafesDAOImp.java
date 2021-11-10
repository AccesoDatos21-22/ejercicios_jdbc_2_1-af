package org.iesinfantaelena.dao;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Cafe;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

//TODO AccesoDatosException

/**
 * Nota: Entiendo que el objetivo del DAO es separar la funcionalidad de la DB,
 * pero ya que el ejercicio pide hacerlo en Cafes.java, lo hago allí
 */
public class CafesDAOImp implements CafesDAO {
	private Cafes cafes;
	
	public CafesDAOImp() {
		cafes = new Cafes();
	}
	
	@Override
	public void verTabla() throws AccesoDatosException {
		System.out.println(cafes.toString());
	}
	
	@Override
	public Cafe buscar(String nombre) throws AccesoDatosException {
		for (Cafe cafe : cafes.getCafes()) {
			if (cafe.getNombre().equals(nombre)) {
				return cafe;
			}
		}
		
		return null;
	}
	
	@Override
	public boolean insertar(String nombre, int provid, float precio, int ventas, int total)
			throws AccesoDatosException {
		Cafe cafe = new Cafe();
		cafe.setNombre(nombre);
		cafe.setProvid(provid);
		cafe.setPrecio(precio);
		cafe.setVentas(ventas);
		cafe.setTotal(total);
		
		return cafes.add(cafe);
	}
	
	@Override
	public boolean borrar(String nombre) throws AccesoDatosException {
		return false;
	}
	
	@Override
	public List<Cafe> cafesPorProveedor(int provid) throws AccesoDatosException {
		List<Cafe> cafesPorProveedor = new ArrayList<>();
		
		for (Cafe cafe : cafes.getCafes()) {
			if (cafe.getProvid() == provid) {
				cafesPorProveedor.add(cafe);
			}
		}
		
		return cafesPorProveedor;
	}
}
