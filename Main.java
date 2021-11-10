import org.iesinfantaelena.dao.Cafes;
import org.iesinfantaelena.dao.CafesDAOImp;
import org.iesinfantaelena.dao.Libros;
import org.iesinfantaelena.modelo.AccesoDatosException;

public class Main {
	public static void main(String[] args) {
		try {
			CafesDAOImp cafesDAO = new CafesDAOImp();
			
			System.out.println(cafesDAO.insertar("Cafetito", 150, 1.0f, 100,1000));
			System.out.println(cafesDAO.insertar("Cafe tacilla", 150, 2.0f, 100,1000));
			cafesDAO.verTabla();
			cafesDAO.buscar("tacilla");
			cafesDAO.cafesPorProveedor(150);
			cafesDAO.borrar("Cafe tacilla");
			cafesDAO.verTabla();
		} catch (AccesoDatosException ex) {
			ex.printStackTrace();
		}
	}

}
