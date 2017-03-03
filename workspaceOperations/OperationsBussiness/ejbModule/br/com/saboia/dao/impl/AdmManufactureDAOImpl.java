package br.com.saboia.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import br.com.saboia.dao.AdmManufactureDAO;
import br.com.saboia.entity.TbAdmMaterialManufacturer;
import br.com.saboia.jpa.GenericDAO;

@Stateless
public class AdmManufactureDAOImpl extends GenericDAO<TbAdmMaterialManufacturer> implements AdmManufactureDAO {

	public AdmManufactureDAOImpl() {
        super(TbAdmMaterialManufacturer.class);
    }
	
	public void save(TbAdmMaterialManufacturer manufacture){
		super.save(manufacture);
	}

	
	public List<TbAdmMaterialManufacturer> findAll(){
		return super.findAll();
	}

	@Override
	public void delete(TbAdmMaterialManufacturer admManufactureSeleconado) {
		Object object = admManufactureSeleconado.getId();
		super.delete(object, TbAdmMaterialManufacturer.class);
	}

	@Override
	public void alter(
			TbAdmMaterialManufacturer admMaterialManufacture) {
		super.update(admMaterialManufacture);		
	}

	@Override
	public TbAdmMaterialManufacturer find(Long id) {
		return super.find(id);
	}
	
}
