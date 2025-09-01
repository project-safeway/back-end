package com.safeway.tech.enums;

public enum NivelEnsinoEnum {

    ENSINO_MEDIO("ENSINO_MEDIO"),
    ENSINO_FUNDAMENTAL("ENSINO_FUNDAMENTAL"),
    CRECHE("CRECHE"),
    PRE_ESCOLA("PRE_ESCOLA");

    private final String nivel;

    NivelEnsinoEnum(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }

    public static NivelEnsinoEnum converterNivel(String codigo) {
        for(NivelEnsinoEnum nivel : values()) {
            if (nivel.nivel.equals(codigo)) return nivel;
        }
        throw new RuntimeException();
    }

}
