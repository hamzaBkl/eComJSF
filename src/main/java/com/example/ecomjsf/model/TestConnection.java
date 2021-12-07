package com.example.ecomjsf.model;

import java.util.ArrayList;
import java.util.List;

import com.example.ecomjsf.dao.CategoryDAO;
import com.example.ecomjsf.service.CategoryDAOImpl;
import com.example.ecomjsf.util.HibernateUtil;
import org.hibernate.Session;


public class TestConnection {

	public static void main(String[] args) {
		//Session session = HibernateUtil.getSessionFactory().openSession();
		 
//		Transaction tx = session.beginTransaction();
//        Query req = session.createQuery("from User u order by u.nom");
//        List<User> allUser = (List<User>) req.list();
//        tx.commit();
//        ArrayList<User> listUser = new ArrayList<User>(allUser);
//        for (Iterator iterator = listUser.iterator(); iterator.hasNext();) {
//			User user = (User) iterator.next();
//			System.out.println(user.getIdUser()+"\t"+user.getNom()+"\t"+user.getMdp());
//		}
        
//        Transaction tx = session.beginTransaction();
//        Query req = session.createQuery("from Categorie c order by c.titre");
//        List<Categorie> allCategorie = (List<Categorie>) req.list();
//        tx.commit();
//        ArrayList<Categorie> listUser = new ArrayList<Categorie>(allCategorie);
//        for (Iterator iterator = listUser.iterator(); iterator.hasNext();) {
//        	Categorie categorie = (Categorie) iterator.next();
//			System.out.println(categorie.getIdCat()+"\t"+categorie.getTitre()+"\t\t"+categorie.getDescription());
//		}
		
		CategoryDAO catDAO = new CategoryDAOImpl();
		List<Category> lstCateg;
		lstCateg = catDAO.listCategories();
		lstCateg.stream().forEach(e -> System.out.println(e));
		
		Category categ = catDAO.getCategoryById(1);
		List<Product> lstProducts;
		lstProducts =  new ArrayList<Product>(categ.getProducts());
		lstProducts.stream().forEach(p -> {
			System.out.print(p);
			System.out.println("   Catgegorie = "+p.getCategory().getIdCat());
		});
	}

}
