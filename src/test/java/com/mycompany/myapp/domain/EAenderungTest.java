package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EAenderungTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EAenderung.class);
        EAenderung eAenderung1 = new EAenderung();
        eAenderung1.setId(1L);
        EAenderung eAenderung2 = new EAenderung();
        eAenderung2.setId(eAenderung1.getId());
        assertThat(eAenderung1).isEqualTo(eAenderung2);
        eAenderung2.setId(2L);
        assertThat(eAenderung1).isNotEqualTo(eAenderung2);
        eAenderung1.setId(null);
        assertThat(eAenderung1).isNotEqualTo(eAenderung2);
    }
}
