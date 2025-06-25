package tobyspring.splearn.domain;


public record Email(
        String address
) {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public Email {
        if(!address.matches(EMAIL_PATTERN)){
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }

    }
}
