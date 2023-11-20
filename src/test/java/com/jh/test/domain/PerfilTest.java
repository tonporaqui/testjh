package com.jh.test.domain;

import static com.jh.test.domain.AppUserTestSamples.*;
import static com.jh.test.domain.PerfilTestSamples.*;
import static com.jh.test.domain.UserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jh.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perfil.class);
        Perfil perfil1 = getPerfilSample1();
        Perfil perfil2 = new Perfil();
        assertThat(perfil1).isNotEqualTo(perfil2);

        perfil2.setId(perfil1.getId());
        assertThat(perfil1).isEqualTo(perfil2);

        perfil2 = getPerfilSample2();
        assertThat(perfil1).isNotEqualTo(perfil2);
    }

    @Test
    void userTest() throws Exception {
        Perfil perfil = getPerfilRandomSampleGenerator();
        User userBack = getUserRandomSampleGenerator();

        perfil.setUser(userBack);
        assertThat(perfil.getUser()).isEqualTo(userBack);
        assertThat(userBack.getPerfil()).isEqualTo(perfil);

        perfil.user(null);
        assertThat(perfil.getUser()).isNull();
        assertThat(userBack.getPerfil()).isNull();
    }

    @Test
    void appUserTest() throws Exception {
        Perfil perfil = getPerfilRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        perfil.setAppUser(appUserBack);
        assertThat(perfil.getAppUser()).isEqualTo(appUserBack);
        assertThat(appUserBack.getPerfil()).isEqualTo(perfil);

        perfil.appUser(null);
        assertThat(perfil.getAppUser()).isNull();
        assertThat(appUserBack.getPerfil()).isNull();
    }
}
