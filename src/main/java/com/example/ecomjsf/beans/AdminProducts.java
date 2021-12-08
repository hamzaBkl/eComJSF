package com.example.ecomjsf.beans;

import com.example.ecomjsf.model.Category;
import com.example.ecomjsf.model.Product;
import com.example.ecomjsf.service.CategoryDAOImpl;
import com.example.ecomjsf.service.ProductDAOImpl;
import com.example.ecomjsf.util.UploadHelper;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "adminProducts", eager = true)
@SessionScoped
public class AdminProducts implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Product> allProducts;
	private List<Product> filteredProducts;

	private List<Category> allCategories;
	private Category productCategory;

	private Long idCategory;
	private Product selectedProduct;
	private Product productToAdd= new Product();

	private String photo = "";
	private Part part;

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	private int product;
	private final ProductDAOImpl productDAO = new ProductDAOImpl();
	private final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

	private boolean editMode = false;
	private boolean addMode = false;

	private final ProductDAOImpl productService;
	{
		productService = new ProductDAOImpl();
	}
	
	@PostConstruct
	public void init(){
		allProducts =getAllProducts();
		allCategories=categoryDAO.listCategories();
		idCategory=allCategories.get(0).getIdCat();
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
		productToAdd =new Product();
		addMode=false;	
	}
	
	public void addProduct(){
		
		if(productToAdd !=null) {
			productToAdd.setCategory(categoryDAO.getCategoryById(idCategory));
			productService.addProduct(productToAdd);

//			UploadHelper uploadHelper = new UploadHelper();
//			this.photo = uploadHelper.processUpload(this.part);
//			productToAdd.setPhoto(this.photo);


				System.out.println("Ajout de la produit avec Succès");
				addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout de la produit avec Succès");
			}else {
				addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout de la produit");
			}
		addMode=false;
	}
	
	public void updateProduct(){
		if(selectedProduct!=null) {
			System.out.println("Updating... => "+ selectedProduct);
			selectedProduct.setCategory(categoryDAO.getCategoryById(selectedProduct.getCategory().getIdCat()));
			productService.updateProduct(selectedProduct);
			System.out.println("Modification de la produit avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification de la produit avec Succès");
		}else {
			addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée", "Erreur lors de la modification de la produit");
		}
		editMode=false;
	}
	
	public void deleteSelectedProduct() {
		productService.removeProduct(selectedProduct.getIdProduct());
	      FacesContext context = FacesContext.getCurrentInstance();
	      context.addMessage("msgDel", new FacesMessage(FacesMessage.SEVERITY_INFO, "Product Supprimée", selectedProduct.toString()));
	      allProducts =getAllProducts();
		}
	
		
	public List<Product> getAllProducts() {
		allProducts = productDAO.listProducts();
		return allProducts;
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

	public List<Product> getFilteredProducts() {
		return filteredProducts;
	}

	public void setFilteredProducts(List<Product> filteredProducts) {
		this.filteredProducts = filteredProducts;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public Category getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Category productCategory) {
		this.productCategory = productCategory;
	}

	public List<Category> getAllCategories() {
		return allCategories;
	}

	public void setAllCategories(List<Category> allCategories) {
		this.allCategories = allCategories;
	}

	public Long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}

	public Product getProductToAdd() {
		return productToAdd;
	}

	public void setProductToAdd(Product productToAdd) {
		this.productToAdd = productToAdd;
	}

}
