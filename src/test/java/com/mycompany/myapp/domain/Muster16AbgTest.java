package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Muster16AbgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Muster16Abg.class);
        Muster16Abg muster16Abg1 = new Muster16Abg();
        muster16Abg1.setId(1L);
        Muster16Abg muster16Abg2 = new Muster16Abg();
        muster16Abg2.setId(muster16Abg1.getId());
        assertThat(muster16Abg1).isEqualTo(muster16Abg2);
        muster16Abg2.setId(2L);
        assertThat(muster16Abg1).isNotEqualTo(muster16Abg2);
        muster16Abg1.setId(null);
        assertThat(muster16Abg1).isNotEqualTo(muster16Abg2);
    }
}
