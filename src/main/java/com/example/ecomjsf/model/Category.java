package com.example.ecomjsf.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="category")
@Proxy(lazy = false)

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(idCat, category.idCat) && Objects.equals(nameCat, category.nameCat) && Objects.equals(description, category.description) && Objects.equals(products, category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCat, nameCat, description, products);
    }

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idCat;
	
    private String nameCat;
    private String description;

	@OneToMany(mappedBy="category")
	private List<Product> products;
	
    public Category() {
    }

    public Long getIdCat() {
        return idCat;
    }

    public void setIdCat(Long idCat) {
        this.idCat = idCat;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public Product addProduit(Product product) {
		getProducts().add(product);
		product.setCategory(this);

		return product;
	}

	public Product removeProduit(Product product) {
		getProducts().remove(product);
		product.setCategory(null);

		return product;
	}

    @Override
    public String toString() {
        return "Category : ( "+idCat+" )  "+nameCat+" : "+description;
    }
	
}
