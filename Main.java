import org.iesinfantaelena.dao.CafeDAOType;
import org.iesinfantaelena.dao.Cafes;
import org.iesinfantaelena.dao.CafesDAOImpSql;
import org.iesinfantaelena.modelo.AccesoDatosException;
import org.iesinfantaelena.utils.Utilidades;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) throws IOException, SQLException {
	/*	try {
			Cafes cafes = new Cafes(CafeDAOType.H2);
			
			cafes.insertar("Cafetito", 150, 1.0f, 100,1000);
			cafes.insertar("Cafe tacilla", 150, 2.0f, 100,1000);
			cafes.verTabla();
			cafes.buscar("tacilla");
			cafes.cafesPorProveedor(150);
			cafes.borrar("Cafe tacilla");
			cafes.verTabla();
			
			cafes.cerrar();
		} catch (AccesoDatosException ex) {
			ex.printStackTrace();
		}*/
		Utilidades util = new Utilidades();
		util.getConnection();
	}
}
