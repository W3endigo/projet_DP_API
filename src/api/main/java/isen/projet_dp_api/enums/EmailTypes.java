package isen.projet_dp_api.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTypes {

    REGISTRATION("Inscription - Lab Manager Research", "templateRegister"),

    PROJECTCREATION("Création d'un nouveau projet", "templateProject"),

    UPDATE_PROFILE("Modification du profil - Lab Manager Research", "templateUpdateUser");


    private final String subject;

    private final String templateName;


}