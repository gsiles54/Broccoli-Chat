package com.servidor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.DB.Usuario;

public class TestHibernate {

	public static void main(String[] args) {
		 SessionFactory factory = new Configuration().configure().buildSessionFactory();
		 Session  session = factory.openSession();
        
		Usuario a = new Usuario();
		a.setID(95);
		a.setUsuario("Pedro");
		a.setPassword("Pedro");
		
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
		
		factory.close();
		session.close();
	}
	
	/*
	public static void testBaseDeDatos() throws SQLException, ClassNotFoundException {
		    final String JDBC_DRIVER = "org.h2.Driver";   
		    final String DB_URL = "jdbc:h2:~/test";  
			 final String USER = "sa"; 
		    final String PASS = ""; 

		    Class.forName("org.h2.Driver");
		    	  Connection conn = DriverManager.getConnection("jdbc:h2:~/ChatDB",USER,PASS); 
		    	//  Connection conn = DriverManager.getConnection("jdbc:h2:~/ChatDB"); 
		          Statement stat = conn.createStatement();
		          //stat.execute("insert into test values(1, 'Hello')");
		    	  ResultSet rs = stat.executeQuery("select * from CHUCK"); 
		    		
	              while (rs.next()) 
	                System.out.println(rs.getString("FACT"));
	              
	              conn.commit();
	              
	              rs.close();
	              stat.close();
	              conn.close();
	}
*/
	//ATENCION, REESTABLECER LO COMENTADO EN COM.DB.DAO.
	/*public static void testDB() {
		try{
		EntityManagerFactory emf= Persistence.createEntityManagerFactory("Persistencia");
		
		EntityManager em= emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(new Usuario(9,"Pedro","Pedro"));
		em.getTransaction().commit();
		}catch(NoClassDefFoundError e) {
			e.printStackTrace();
			}
	}*/
	/*
	public static void testBaseDeDatosHibernate() {

		Usuario user = new Usuario(7, "Cristian", "Cristian");
		EntityManager a;

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.save(user);
		
		session.getTransaction().commit();
		
		session.close();
		sessionFactory.close();
	}
	*/

}
