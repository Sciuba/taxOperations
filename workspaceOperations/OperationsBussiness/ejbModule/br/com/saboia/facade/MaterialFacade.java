package br.com.saboia.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.saboia.entity.TbAdmMaterial;

@Local
public interface MaterialFacade {
	
	List<TbAdmMaterial> findAll();
	
	List<TbAdmMaterial> findLazyDataModel(int startingAt, int maxPerPage, String filtroSelecionado, String filtroManufacturer, String filtroPartNumber, String filtroDescription, String filtroNCM );
	
	int selectTotalNumberRegistry( String filtroSelecionado, String filtroManufacturer, String filtroPartNumber, String filtroDescription, String filtroNCM);
	
	void save(TbAdmMaterial material);
	
	void alter(TbAdmMaterial material);
	
	void delete(TbAdmMaterial material);
	
	TbAdmMaterial find(Long id);

	TbAdmMaterial saveReturn(TbAdmMaterial material);
	
	TbAdmMaterial findQuery(Object... params);
	
	

}
