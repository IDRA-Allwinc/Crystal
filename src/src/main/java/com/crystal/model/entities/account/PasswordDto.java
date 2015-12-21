package com.crystal.model.entities.account;

import com.crystal.infrastructure.validation.FieldMatch;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;

@FieldMatch(first = "password", second = "confirm", message = "La contraseña no concuerda con la confirmación.")
public class PasswordDto{
    private Long id;

    @NotEmpty(message = "La contraseña es un campo requerido.")
    @Length(min = 8, max  = 200, message = "La contraseña debe tener entre 8 y 200 caracteres.")
    private String password;

    @NotEmpty(message = "La confirmación es un campo requerido.")
    private String confirm;


    public PasswordDto(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
