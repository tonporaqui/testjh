package com.jh.test.domain;

import static com.jh.test.domain.AppUserTestSamples.*;
import static com.jh.test.domain.PerfilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jh.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void perfilTest() throws Exception {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Perfil perfilBack = getPerfilRandomSampleGenerator();

        appUser.setPerfil(perfilBack);
        assertThat(appUser.getPerfil()).isEqualTo(perfilBack);

        appUser.perfil(null);
        assertThat(appUser.getPerfil()).isNull();
    }
}
