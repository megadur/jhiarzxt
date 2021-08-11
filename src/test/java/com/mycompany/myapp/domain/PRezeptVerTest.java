package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PRezeptVerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PRezeptVer.class);
        PRezeptVer pRezeptVer1 = new PRezeptVer();
        pRezeptVer1.setId(1L);
        PRezeptVer pRezeptVer2 = new PRezeptVer();
        pRezeptVer2.setId(pRezeptVer1.getId());
        assertThat(pRezeptVer1).isEqualTo(pRezeptVer2);
        pRezeptVer2.setId(2L);
        assertThat(pRezeptVer1).isNotEqualTo(pRezeptVer2);
        pRezeptVer1.setId(null);
        assertThat(pRezeptVer1).isNotEqualTo(pRezeptVer2);
    }
}
