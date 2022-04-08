package ac.za.dirisa.sst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ac.za.dirisa.sst.model.ConfirmationToken;
import ac.za.dirisa.sst.model.User;
import ac.za.dirisa.sst.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ConfirmationTokenService confirmationTokenService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException(String.format("user not found", email))
        );
    }

	public String signUpUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
				          LocalDateTime.now().plusDays(1), user);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		return token;
	}

	public void enableUser(String email) {
		userRepository.enableUser(email);
	}

	public void updateResetPasswordToken(String token, String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setResetPasswordToken(token);
			userRepository.save(user);
		}
	}

	public User getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	public void updatePassword(User user, String password) {
		user.setPassword(password);
		user.setResetPasswordToken(null);
		userRepository.save(user);
	}

	/**
	 * changeUserPassword Update password when logged in as a user
	 * @param user
	 * @param password
	 * @return void
	 */
	public void changeUserPassword(User user, String password) {
		String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }	

	/**
	 * findUserByEmail find user by email Address
	 * @param email
	 * @return Optional user
	 */
	public Optional<User> findUserByEmailAddress(String email){
		Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser;
	}
	
	public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

	public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}