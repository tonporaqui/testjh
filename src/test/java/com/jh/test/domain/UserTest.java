package com.jh.test.domain;

import static com.jh.test.domain.PerfilTestSamples.*;
import static com.jh.test.domain.UserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jh.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User.class);
        User user1 = getUserSample1();
        User user2 = new User();
        assertThat(user1).isNotEqualTo(user2);

        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);

        user2 = getUserSample2();
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void perfilTest() throws Exception {
        User user = getUserRandomSampleGenerator();
        Perfil perfilBack = getPerfilRandomSampleGenerator();

        user.setPerfil(perfilBack);
        assertThat(user.getPerfil()).isEqualTo(perfilBack);

        user.perfil(null);
        assertThat(user.getPerfil()).isNull();
    }
}
