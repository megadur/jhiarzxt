package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ERezeptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ERezept.class);
        ERezept eRezept1 = new ERezept();
        eRezept1.setId(1L);
        ERezept eRezept2 = new ERezept();
        eRezept2.setId(eRezept1.getId());
        assertThat(eRezept1).isEqualTo(eRezept2);
        eRezept2.setId(2L);
        assertThat(eRezept1).isNotEqualTo(eRezept2);
        eRezept1.setId(null);
        assertThat(eRezept1).isNotEqualTo(eRezept2);
    }
}
