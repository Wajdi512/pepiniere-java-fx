package application.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import application.model.Absence;
import application.model.Avance;
import application.model.BonDeLivraison;
import application.model.Commande;
import application.model.CoutDeJour;
import application.model.Devis;
import application.model.Facture;
import application.model.Ouvrier;
import application.model.Panier;
import application.model.Produit;
import application.model.Tranche;
import application.model.User;

public class DBConnexion {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			return new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Produit.class)
				.addAnnotatedClass(Facture.class)
				.addAnnotatedClass(Panier.class)
				.addAnnotatedClass(Tranche.class)
				.addAnnotatedClass(BonDeLivraison.class)
				.addAnnotatedClass(Commande.class)
				.addAnnotatedClass(Ouvrier.class)
				.addAnnotatedClass(Absence.class)
				.addAnnotatedClass(Devis.class)
				.addAnnotatedClass(Avance.class)
				.addAnnotatedClass(CoutDeJour.class)
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		}
		else {
			return sessionFactory;
		}
	}


}
