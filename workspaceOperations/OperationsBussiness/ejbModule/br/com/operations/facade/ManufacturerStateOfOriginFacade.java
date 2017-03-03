package br.com.operations.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmManufacturerStateOfOrigin;

@Local
public interface ManufacturerStateOfOriginFacade {
	
	List<TbAdmManufacturerStateOfOrigin> findAll();

	void save(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
	
	void alter(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
	
	void delete(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);
	
	TbAdmManufacturerStateOfOrigin find(long id);
	
	TbAdmManufacturerStateOfOrigin saveReturn(TbAdmManufacturerStateOfOrigin admManufacturerStateOfOrigin);

}
