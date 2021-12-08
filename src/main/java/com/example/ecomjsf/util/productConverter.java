package com.example.ecomjsf.util;

import com.example.ecomjsf.model.Product;
import com.example.ecomjsf.service.ProductDAOImpl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@ManagedBean
@RequestScoped
@FacesConverter(forClass=Product.class)
public class productConverter implements Converter {

    private final ProductDAOImpl productDAO = new ProductDAOImpl();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return productDAO.getProductById(Long.parseLong(value));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid Product ID", value)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Product) {
            return String.valueOf(((Product) value).getIdProduct());
        } else {
            throw new ConverterException(new FacesMessage(String.format("%s is not a valid Product", value)));
        }
    }
}
