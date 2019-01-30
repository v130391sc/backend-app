package es.upm.fi.catering.service.backendapp.model;

public enum UserTypeEnum {

	ADMIN("Administrador"),
	USER("Usuario");

	private final String value;

    UserTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserTypeEnum fromValue(String v) {
        for (UserTypeEnum c: UserTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
