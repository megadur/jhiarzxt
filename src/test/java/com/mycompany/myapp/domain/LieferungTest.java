package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LieferungTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lieferung.class);
        Lieferung lieferung1 = new Lieferung();
        lieferung1.setId(1L);
        Lieferung lieferung2 = new Lieferung();
        lieferung2.setId(lieferung1.getId());
        assertThat(lieferung1).isEqualTo(lieferung2);
        lieferung2.setId(2L);
        assertThat(lieferung1).isNotEqualTo(lieferung2);
        lieferung1.setId(null);
        assertThat(lieferung1).isNotEqualTo(lieferung2);
    }
}
