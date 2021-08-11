package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MArtikelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MArtikel.class);
        MArtikel mArtikel1 = new MArtikel();
        mArtikel1.setId(1L);
        MArtikel mArtikel2 = new MArtikel();
        mArtikel2.setId(mArtikel1.getId());
        assertThat(mArtikel1).isEqualTo(mArtikel2);
        mArtikel2.setId(2L);
        assertThat(mArtikel1).isNotEqualTo(mArtikel2);
        mArtikel1.setId(null);
        assertThat(mArtikel1).isNotEqualTo(mArtikel2);
    }
}
