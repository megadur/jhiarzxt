package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApothekeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apotheke.class);
        Apotheke apotheke1 = new Apotheke();
        apotheke1.setId(1L);
        Apotheke apotheke2 = new Apotheke();
        apotheke2.setId(apotheke1.getId());
        assertThat(apotheke1).isEqualTo(apotheke2);
        apotheke2.setId(2L);
        assertThat(apotheke1).isNotEqualTo(apotheke2);
        apotheke1.setId(null);
        assertThat(apotheke1).isNotEqualTo(apotheke2);
    }
}
