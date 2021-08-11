package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Muster16AbrTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Muster16Abr.class);
        Muster16Abr muster16Abr1 = new Muster16Abr();
        muster16Abr1.setId(1L);
        Muster16Abr muster16Abr2 = new Muster16Abr();
        muster16Abr2.setId(muster16Abr1.getId());
        assertThat(muster16Abr1).isEqualTo(muster16Abr2);
        muster16Abr2.setId(2L);
        assertThat(muster16Abr1).isNotEqualTo(muster16Abr2);
        muster16Abr1.setId(null);
        assertThat(muster16Abr1).isNotEqualTo(muster16Abr2);
    }
}
