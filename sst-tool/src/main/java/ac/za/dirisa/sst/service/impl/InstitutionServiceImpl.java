package ac.za.dirisa.sst.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.za.dirisa.sst.model.Institution;
import ac.za.dirisa.sst.repository.InstitutionRepository;
import ac.za.dirisa.sst.service.InstitutionService;

@Service
public class InstitutionServiceImpl implements InstitutionService{

	@Autowired
	InstitutionRepository institutionRepository;
	
	@Override
	public List<Institution> findInstitutions() {
		return institutionRepository.findAll();
	}

	@Override
	public Optional<Institution> findById(Long id) {
		return institutionRepository.findById(id);
	}
}
