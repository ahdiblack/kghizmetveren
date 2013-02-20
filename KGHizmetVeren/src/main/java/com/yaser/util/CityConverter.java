package com.yaser.util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.yaser.data.dao.TblCityDAO;
import com.yaser.data.model.TblCity;

@Component
@ManagedBean(name="cityConverter")
public class CityConverter implements Converter {

	@Inject
    private TblCityDAO tblCityDAO;
 
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return tblCityDAO.findOne(Long.valueOf(value));
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return String.valueOf(((TblCity) value).getCityId());
    }
}