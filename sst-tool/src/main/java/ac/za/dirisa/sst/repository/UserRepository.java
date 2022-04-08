package ac.za.dirisa.sst.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ac.za.dirisa.sst.model.User;


@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
        
    @Modifying
    @Transactional
    @Query(value = "UPDATE users u SET u.enabled = TRUE WHERE u.email = ?1", nativeQuery = true)
    void enableUser(String email);
    
	User findByResetPasswordToken(String token);
	
	@Query("SELECT u FROM User u WHERE u.role = 'USER'")
	List<User> getUsersByRole();
}
