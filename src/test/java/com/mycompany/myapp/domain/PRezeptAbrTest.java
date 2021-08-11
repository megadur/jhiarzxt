package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PRezeptAbrTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PRezeptAbr.class);
        PRezeptAbr pRezeptAbr1 = new PRezeptAbr();
        pRezeptAbr1.setId(1L);
        PRezeptAbr pRezeptAbr2 = new PRezeptAbr();
        pRezeptAbr2.setId(pRezeptAbr1.getId());
        assertThat(pRezeptAbr1).isEqualTo(pRezeptAbr2);
        pRezeptAbr2.setId(2L);
        assertThat(pRezeptAbr1).isNotEqualTo(pRezeptAbr2);
        pRezeptAbr1.setId(null);
        assertThat(pRezeptAbr1).isNotEqualTo(pRezeptAbr2);
    }
}
