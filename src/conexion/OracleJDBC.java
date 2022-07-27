package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJDBC{

	private static OracleJDBC instancia = null;
	private static Connection conn;
	
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USUARIO = "alumno";
	private final String PASSWORD = "Curso2022";
	private final String SQL_USE = "USE";
	private final String SQL_CREATE = "CREATE DATABASE";

	
	  

	public static void main(String[] argV) {
		conn = null;
		OracleJDBC ojdbc = OracleJDBC.getInstance();
	}



public static OracleJDBC getInstance() {
	if (instancia ==null) {
		instancia = new OracleJDBC();
		
	}
		return instancia;
}

private OracleJDBC() {
	conexion();
}

private void conexion() {
	System.out.println("------Prueba de conexion a BBDD -------");
	
	try {
	Class.forName(JDBC_DRIVER);
	} catch (ClassNotFoundException e) {
		System.out.println("Donde esta tu Driver Oracle");
		e.printStackTrace();
		return;
	}
			
	System.out.println("Driver oracle Registrado!");
	//Conection conn = null;
	try {
	     conn = DriverManager.getConnection(URL , USUARIO , PASSWORD);
		
	} catch (SQLException e) {
		System.out.println("Falló la conexion");
		e.printStackTrace();
		return;
	}
	

}

public int ejecutar(String sql) throws Exception {
	
	System.out.println("Ejecutar: " + sql);
	Statement stm = null;
	int retorno;
	
	try {
		stm = conn.createStatement();
		retorno = stm.executeUpdate(sql);
		
	}catch (SQLException e) {
		e.printStackTrace();
		throw (new Exception("Error en " + sql));
		
	} finally {
		closeStatement(stm);
	}
	
	
	
	return retorno;
}

private void closeStatement(Statement stm) {
	// TODO Auto-generated method stub
	
}



public ResultSet ejecutarQuery(String sql) throws Exception {
	
	System.out.println("Ejecutar Query: " + sql);
	Statement stm = null;
	ResultSet retorno;
	
	try {
		stm = conn.createStatement();
		retorno = stm.executeQuery(sql);
		
	}catch (SQLException e) {
		e.printStackTrace();
		throw (new Exception("Error en " + sql));
		
	} finally {
		//closeStatement(stm);
	}
	
	return retorno;
}



public int consigueClave(String tabla, String campo) throws Exception {
	String sql = "SELECT MAX(" + campo + ") FROM " + tabla;
	ResultSet rs = ejecutarQuery(sql);
	
	try {
		if (rs.next())
			System.out.println("Tiene datos");
		else
			return 1;
		return rs.getInt(0) + 1 ; 
	}catch (SQLException e) {
		e.printStackTrace();
		throw new Exception("Error buscando PK : " + sql);
	}
	
}



public void closeConnection() throws Exception {
	try {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	} catch (Exception e) {
		System.out.println("No se ha podido cerrar la conexión a la BD");
		throw new Exception("Error al cerrar conexión a BD");
	} finally {
		conn = null;
	}
}
}







