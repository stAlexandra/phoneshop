package com.es.core.model.phone;

public class Color {
    private Long id;
    private String code;

    public Color() {
    }

    public Color(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
