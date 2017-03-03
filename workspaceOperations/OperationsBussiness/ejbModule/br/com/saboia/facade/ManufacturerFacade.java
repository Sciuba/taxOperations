package br.com.saboia.facade;

import java.util.List;
import javax.ejb.Local;
import br.com.saboia.entity.TbAdmMaterialManufacturer;

@Local
public interface ManufacturerFacade {
	
	List<TbAdmMaterialManufacturer> findAll();

	void save(TbAdmMaterialManufacturer admManufactureSeleconado);
	
	void alter(TbAdmMaterialManufacturer admManufacturer);
	
	void delete(TbAdmMaterialManufacturer admManufactureSeleconado);
	
	TbAdmMaterialManufacturer find (Long id);

}
