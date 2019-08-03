package application;

import java.util.Date;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.model.Panier;
import application.model.Produit;

public class TestDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Produit.class)
				.addAnnotatedClass(Panier.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
*/
		try{
			/*session.beginTransaction();
			Produit p = new Produit("test", 50, 48, 100, null, true);
			session.save(p);
			System.out.println(p);
			Panier panier = new Panier();
			panier.setQuantite(20);
		//	panier.setProduit(p);
			session.save(panier);
			session.getTransaction().commit();*/
			ProduitDao pd = new ProduitDaoImpl();
			System.out.println(pd.listProduit());



		}finally {

		//	factory.close();
		}

	}

}
