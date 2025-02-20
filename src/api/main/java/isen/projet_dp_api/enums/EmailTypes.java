package isen.projet_dp_api.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTypes {

    REGISTRATION("Inscription - Lab Manager Research", "templateRegister"),

    UPDATE_PROFILE("Modification du profil - Lab Manager Research", "templateUpdateUser");


    private final String subject;

    private final String templateName;


}