package com.example.ecomjsf.service;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.ecomjsf.dao.CategoryDAO;
import com.example.ecomjsf.model.Category;
import com.example.ecomjsf.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;




public class CategoryDAOImpl implements CategoryDAO {
	private static final Logger logger = Logger.getLogger(CategoryDAOImpl.class.getName());

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not create SessionFactory", e);
			throw new IllegalStateException("Could not create SessionFactory");
		}
	} 
	
	@Override
	public void addCategory(Category category) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(category);
		session.getTransaction().commit();
		logger.info("Category saved successfully, Category Details="+category);
	}

	@Override
	public void updateCategory(Category category) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.update(category);
		session.getTransaction().commit();
		logger.info("Category updated successfully, Category Details="+category);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Category> listCategories() {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Category> CategoriesList = session.createQuery("from Category").list();
		session.getTransaction().commit();
		return CategoriesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> selectCatByKeyword(String keyWord) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Category> CategoriesList = session.createQuery("from Category c WHERE c.nameCat LIKE '%"+keyWord+"%'").list();
		session.getTransaction().commit();
		
		return CategoriesList;
	}
	@Override
	public Category getCategoryById(long id) {
		Session session = this.sessionFactory.getCurrentSession();	
		session.beginTransaction();
		Category category = session.load(Category.class, id);
		session.getTransaction().commit();
		//logger.info("Category loaded successfully, Category details="+category);
		return category;
	}

	@Override
	public void removeCategory(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Category category = session.load(Category.class, id);
		
		if(null != category){
			session.delete(category);
		}
		session.getTransaction().commit();
		logger.info("Category deleted successfully, Category details="+category);
	}
}
