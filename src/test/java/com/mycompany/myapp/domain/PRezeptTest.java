package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PRezeptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PRezept.class);
        PRezept pRezept1 = new PRezept();
        pRezept1.setId(1L);
        PRezept pRezept2 = new PRezept();
        pRezept2.setId(pRezept1.getId());
        assertThat(pRezept1).isEqualTo(pRezept2);
        pRezept2.setId(2L);
        assertThat(pRezept1).isNotEqualTo(pRezept2);
        pRezept1.setId(null);
        assertThat(pRezept1).isNotEqualTo(pRezept2);
    }
}
