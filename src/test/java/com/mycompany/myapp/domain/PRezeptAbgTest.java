package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PRezeptAbgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PRezeptAbg.class);
        PRezeptAbg pRezeptAbg1 = new PRezeptAbg();
        pRezeptAbg1.setId(1L);
        PRezeptAbg pRezeptAbg2 = new PRezeptAbg();
        pRezeptAbg2.setId(pRezeptAbg1.getId());
        assertThat(pRezeptAbg1).isEqualTo(pRezeptAbg2);
        pRezeptAbg2.setId(2L);
        assertThat(pRezeptAbg1).isNotEqualTo(pRezeptAbg2);
        pRezeptAbg1.setId(null);
        assertThat(pRezeptAbg1).isNotEqualTo(pRezeptAbg2);
    }
}
