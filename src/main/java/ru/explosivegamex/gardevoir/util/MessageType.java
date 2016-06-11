package ru.explosivegamex.gardevoir.util;

public enum MessageType {
    INFO("&7"),
    SUCCESS("&2&l"),
    ERROR("&c&l");

    private String code;

    MessageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
