package org.iesinfantaelena.dao;

import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.modelo.Cafe;

import java.util.List;

public interface CafesDAO {
	public void verTabla() throws AccesoDatosException;
	
	public Cafe buscar(String nombre) throws AccesoDatosException;
	
	public boolean insertar(String nombre, int provid, float precio, int ventas, int total)
			throws AccesoDatosException;
	
	public boolean borrar(String nombre) throws AccesoDatosException;

	public List<Cafe> cafesPorProveedor(int provid) throws AccesoDatosException;
}
