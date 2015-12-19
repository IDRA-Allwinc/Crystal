package com.crystal.service.account;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.security.CryptoRfc2898;
import com.crystal.model.entities.account.User;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SharedUserService {

    @Autowired
    private UserRepository userRepository;
    private List<String> lstRolesByUserId;

    public Long GetLoggedUserId() {
        try {
            String sUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            Long idUser = userRepository.findIdByUsername(sUsername);
            return idUser;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return -1L;
    }


    public String GetLoggedUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception ex) {
            return "@NA";
        }
    }

    public Boolean isEnabled(Long userId) {
        return userRepository.isEnabled(userId);
    }

    public boolean isValidUser(User user, ResponseMessage response) {
        String sUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User userToValidate = userRepository.getInfoToValidate(sUsername);

        if (userToValidate.getEnabled().equals(false)) {
            response.setMessage("Usted no tiene permisos para realizar esta acci&oacute;n. Por favor solicite los permisos suficientes para realizar esta acci&oacute;n e intente de nuevo.");
            response.setHasError(true);
            return false;
        }

        user.setUsername(sUsername);
        user.setId(userToValidate.getId());
        user.setEnabled(userToValidate.getEnabled());

        return true;
    }

    public Integer calculateAge(Date birthDate) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthDate);
        Calendar today = Calendar.getInstance();
        Integer age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
            age--;
        return age;
    }

    public boolean isValidPasswordForUser(Long userId, String password) {
        String encodePassword = userRepository.getEncodedPassword(userId);
        CryptoRfc2898 cryptoRfc2898 = new CryptoRfc2898();
        return cryptoRfc2898.matches(password.subSequence(0, password.length()), encodePassword);
    }
}
