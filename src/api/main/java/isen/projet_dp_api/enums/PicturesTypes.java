package isen.projet_dp_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PicturesTypes {

    LOGO("static/pictures/logo-color-no-bg.png", "logoImage");

    private final String imagePath;

    private final String imageVarName;

}
