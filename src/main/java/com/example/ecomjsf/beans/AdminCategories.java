package com.example.ecomjsf.beans;

import com.example.ecomjsf.model.Category;
import com.example.ecomjsf.model.Product;
import com.example.ecomjsf.service.CategoryDAOImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean(name = "adminCategories", eager = true)
@SessionScoped
public class AdminCategories implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Category> allCategories;
	private List<Category> filteredCategories;

	private List<Product> allProducts;
	private List<Product> categoryProducts;
	private Category selectedCategory;
	private Category categoryToAdd = new Category();
	private int category;
	private Product productToAdd = new Product();
	private CategoryDAOImpl categDao = new CategoryDAOImpl();
	private boolean editMode = false;
	private boolean addMode = false;
	
	private CategoryDAOImpl categService;
	{
		categService = new CategoryDAOImpl();
	}
	
	@PostConstruct
	public void init(){
		allCategories =getAllCategories();
	}
	
	public void edit(){
		System.out.println("edit clicked");
		editMode=true;
		addMode=false;
	}
	public void cancelUpdate(){
		editMode=false;
	}

	public void prepareAdd(){
		addMode=true;
		editMode=false;
	}
	public void cancelAdd(){
		categoryToAdd =new Category();
		addMode=false;	
	}
	
	public void addCategory(){
		
		if(categoryToAdd !=null) {
			categService.addCategory(categoryToAdd);
			System.out.println("Ajout de la catégorie avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout de la catégorie avec Succès");
		}else {
			addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout de la catégorie");
		}
		addMode=false;
	}
	
	public void updateCategory(){
		if(selectedCategory!=null) {
			System.out.println("Updating... => "+ selectedCategory);
			categService.updateCategory(selectedCategory);
			System.out.println("Modification de la catégorie avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification de la catégorie avec Succès");
		}else {
			addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée", "Erreur lors de la modification de la catégorie");
		}
		editMode=false;
	}
	
	public void deleteSelectedCategory() {
		categService.removeCategory(selectedCategory.getIdCat());
	      FacesContext context = FacesContext.getCurrentInstance();
	      context.addMessage("msgDel", new FacesMessage(FacesMessage.SEVERITY_INFO, "Category Supprimée", selectedCategory.toString()));
	      allCategories =getAllCategories();
		}
	
//	public List<Product> getCategoriyProducts() {
//		Category categ = categDao.getCategoryById(category);
//		categoryProducts = new ArrayList<Product>(categ.getProducts());
//		return categoryProducts;
//	}
//
		
	public List<Category> getAllCategories() {
		allCategories = categDao.listCategories();
		return allCategories;
	}
	

	public Product getProductToAdd() {
		return productToAdd;
	}

	public void setProductToAdd(Product productToAdd) {
		this.productToAdd = productToAdd;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setAllCategories(List<Category> allCategories) {
		this.allCategories = allCategories;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<Product> allProducts) {
		this.allProducts = allProducts;
	}

	public List<Product> getCategoryProducts() {
		return categoryProducts;
	}

	public void setCategoryProducts(List<Product> categoryProducts) {
		this.categoryProducts = categoryProducts;
	}


	public Category getSelectedCategory() {
		return selectedCategory;
	}


	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}


	public Category getCategoryToAdd() {
		return categoryToAdd;
	}


	public void setCategoryToAdd(Category categoryToAdd) {
		this.categoryToAdd = categoryToAdd;
	}


	public boolean isEditMode() {
		return editMode;
	}


	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}


	public boolean isAddMode() {
		return addMode;
	}


	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

	public List<Category> getFilteredCategories() {
		return filteredCategories;
	}

	public void setFilteredCategories(List<Category> filteredCategories) {
		this.filteredCategories = filteredCategories;
	}
}
