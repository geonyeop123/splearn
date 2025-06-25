package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static tobyspring.splearn.domain.MemberStatus.DEACTIVATED;
import static tobyspring.splearn.domain.MemberStatus.PENDING;

class MemberTest {

    Member member;
    String password;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }
            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        password = "secret";
        member = Member.create("geonyeop123@gmail.com", "yeop", password, passwordEncoder);
    }

    @DisplayName("회원을 생성하면 회원 상태는 대기이다.")
    @Test
    void createMember() {
        // given // when

        // then
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }

    @DisplayName("회원을 활성화하면 화원 상태는 ACTIVE이다.")
    @Test
    void activate() {
        // given // when
        member.activate();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @DisplayName("회원을 활성화할 때 현재 상태가 PENDING이 아니라면 IllegalStateException이 발생한다.")
    @Test
    void activateFail() {
        // given
        member.activate();
        // when // then
        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("회원을 비활성화 할 수 있다.")
    @Test
    void deactivate() {
        // given
        member.activate();

        // when
        member.deactivate();

        // then
        assertThat(member.getStatus()).isEqualTo(DEACTIVATED);
    }

    @DisplayName("회원을 비활성화 할 때 회원의 상태가 ACTIVE가 아니라면 IllegalStateException이 발생한다.")
    @Test
    void deactivateFail() {
        // given // when // then
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
        member.activate();
        member.deactivate();
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("넘겨받은 비밀번호가 실제 비밀번호와 일치하는지 확인할 수 있다.")
    @Test
    void verifyPassword() {
        // given // when // then
        assertThat(member.verifyPassword(password, passwordEncoder)).isTrue();
    }

    @DisplayName("닉네임을 변경할 수 있다.")
    @Test
    void changeNickname() {
        // given // when // then
        member.changeNickname("newYeop");

        assertThat(member.getNickname()).isEqualTo("newYeop");
    }

    @DisplayName("비밀번호를 변경할 수 있다.")
    @Test
    void changePassword() {
        // given // when // then
        member.changePassword("verySecret", passwordEncoder);
        assertThat(member.verifyPassword("verySecret", passwordEncoder)).isTrue();
    }

}