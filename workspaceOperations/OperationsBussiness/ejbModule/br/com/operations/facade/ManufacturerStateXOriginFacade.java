package br.com.operations.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.operations.entity.TbAdmManufacturerStateXTbAdmOrigin;

@Local
public interface ManufacturerStateXOriginFacade {
	
	List<TbAdmManufacturerStateXTbAdmOrigin> findAll();
	
	void save(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin);
	
	void alter(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin);
	
	void delete(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin);
	
	TbAdmManufacturerStateXTbAdmOrigin find(Long id);

	TbAdmManufacturerStateXTbAdmOrigin saveReturn(TbAdmManufacturerStateXTbAdmOrigin stateXTbAdmOrigin);
	
	TbAdmManufacturerStateXTbAdmOrigin findQuery(Object... params);

}
