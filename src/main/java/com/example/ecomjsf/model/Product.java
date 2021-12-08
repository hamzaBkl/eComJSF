package com.example.ecomjsf.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProduct;
    private String designation;
    private Boolean selected;
    private Double price;
    private Integer quantity;
    private String photo;
    
    @ManyToOne
	@JoinColumn(name="idCat")
    private Category category;

    public Product() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void isSelected(Boolean selected) {
        this.selected = selected;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(idProduct, product.idProduct) && Objects.equals(designation, product.designation) && Objects.equals(selected, product.selected) && Objects.equals(price, product.price) && Objects.equals(quantity, product.quantity) && Objects.equals(photo, product.photo) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, designation, selected, price, quantity, photo, category);
    }
}
