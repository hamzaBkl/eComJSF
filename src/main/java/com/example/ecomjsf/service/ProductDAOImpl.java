package com.example.ecomjsf.service;

import com.example.ecomjsf.dao.ProductDAO;
import com.example.ecomjsf.model.Product;
import com.example.ecomjsf.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ProductDAOImpl implements ProductDAO {

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
    public void addProduct(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        logger.info("Product saved successfully, Product Details="+product);
    }

    @Override
    public void updateProduct(Product product) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        logger.info("Product updated successfully, Product Details="+product);
    }

    @Override
    public List<Product> listProducts() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("from Product ").list();
        session.getTransaction().commit();
        return products;
    }

    @Override
    public List<Product> selectProductByKeyword(String keyWord) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("from Product p WHERE p.designation LIKE '%"+keyWord+"%'").list();
        session.getTransaction().commit();

        return products;    }

    @Override
    public List<Product> selectProductByCategory(long idCat) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("from Product p WHERE p.category.idCat ="+idCat).list();
        session.getTransaction().commit();

        return products;    }

    @Override
    public List<Product> searchProduct(String keyWord, long idCat) {
        List<Product> products;
        String query;

      //  session.beginTransaction();
        if(idCat==0){
            products=selectProductByKeyword(keyWord);
        }else {
            Session session = this.sessionFactory.getCurrentSession();
            session.beginTransaction();
            products = session.createQuery("from Product p WHERE p.designation LIKE '%"+keyWord+"%' AND p.category.idCat ="+idCat).list();
            session.getTransaction().commit();
            // products = session.createQuery("from Product p WHERE p.designation LIKE '%"+keyWord+"%' AND p.category.idCat ="+idCat).list();
        }
      //  session.getTransaction().commit();

        return products;
    }

    @Override
    public Product getProductById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.load(Product.class, id);
        session.getTransaction().commit();

        return product;
    }

    @Override
    public void removeProduct(long id) {
        Session session=this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product=session.load(Product.class,id);
        if (product!=null){
            session.delete(product);
        }
        session.getTransaction().commit();

        logger.info("Product deleted successfully, Product details="+product);

    }
}
