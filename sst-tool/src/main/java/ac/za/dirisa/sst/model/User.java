package ac.za.dirisa.sst.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ac.za.dirisa.sst.model.enums.UserRole;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String password;

	@Column(name = "reset_password_token")
	private String resetPasswordToken;	

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private Boolean locked = false;

	private Boolean enabled = false;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "user_institution", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "institution_id", unique = false))
	private Institution institution;

	public User() {
	}

	public User(String firstName, String lastName, String email, String password, UserRole role, String description,
			Institution institution) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.description = description;
		this.institution = institution;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	// user details
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());

		return Collections.singletonList(grantedAuthority);
	}

	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}
}