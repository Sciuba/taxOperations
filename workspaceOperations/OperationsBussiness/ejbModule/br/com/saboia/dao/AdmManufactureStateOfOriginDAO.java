package br.com.saboia.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmManufacturerStateOfOrigin;

@Local
public interface AdmManufactureStateOfOriginDAO {
	
	List<TbAdmManufacturerStateOfOrigin> findAll();

	void save(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
	
	void alter(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
	
	void delete(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
	
	TbAdmManufacturerStateOfOrigin find(long id);

	TbAdmManufacturerStateOfOrigin saveReturn(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
}
