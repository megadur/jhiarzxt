package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RechenzentrumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rechenzentrum.class);
        Rechenzentrum rechenzentrum1 = new Rechenzentrum();
        rechenzentrum1.setId(1L);
        Rechenzentrum rechenzentrum2 = new Rechenzentrum();
        rechenzentrum2.setId(rechenzentrum1.getId());
        assertThat(rechenzentrum1).isEqualTo(rechenzentrum2);
        rechenzentrum2.setId(2L);
        assertThat(rechenzentrum1).isNotEqualTo(rechenzentrum2);
        rechenzentrum1.setId(null);
        assertThat(rechenzentrum1).isNotEqualTo(rechenzentrum2);
    }
}
