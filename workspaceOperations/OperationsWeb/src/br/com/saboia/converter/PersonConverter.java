package br.com.saboia.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.saboia.entity.TbSysUser;
import br.com.saboia.facade.UserFacade;

@FacesConverter("br.com.saboia.converter.PersonConverter")
public class PersonConverter implements Converter{

	@EJB
	private UserFacade userFacade;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		TbSysUser person = userFacade.find(Long.parseLong(value));
		
		return person;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		return value.toString();
	}

}
