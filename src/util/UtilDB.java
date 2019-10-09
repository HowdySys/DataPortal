package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import datamodel.StockQuote;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UtilDB {
	static SessionFactory sessionFactory = null;

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public static void insertSymbol(String attributesJson) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction(); 
			session.save(new StockQuote(attributesJson)); 
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close(); 
		}
	}

	public static List<StockQuote> selectSymbol(ArrayList<String> param) {
		Session session = getSessionFactory().openSession(); 
		Transaction tx = null; 
		List<StockQuote> result = new ArrayList<>(); 
		try {
			tx = session.beginTransaction();
			List<?> quotes = session.createQuery("FROM StockQuote").list();
			for (Iterator<?> iterator = quotes.iterator(); iterator.hasNext();) {
				StockQuote sqc = (StockQuote) iterator.next();
				if (param == null || param.isEmpty() || param.contains(sqc.getSymbol()))
					result.add(sqc);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result; 
	}

}
