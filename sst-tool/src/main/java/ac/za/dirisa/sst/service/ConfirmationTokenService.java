package ac.za.dirisa.sst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.za.dirisa.sst.model.ConfirmationToken;
import ac.za.dirisa.sst.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

	@Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {

        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> findToken(String token) {

        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}