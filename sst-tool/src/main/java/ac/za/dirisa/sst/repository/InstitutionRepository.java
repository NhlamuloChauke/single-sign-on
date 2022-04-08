package ac.za.dirisa.sst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ac.za.dirisa.sst.model.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>{
}
