package ac.za.dirisa.sst.service;

import java.util.List;
import java.util.Optional;

import ac.za.dirisa.sst.model.Institution;

public interface InstitutionService {
	List<Institution> findInstitutions();
	Optional<Institution> findById(Long id);
}
