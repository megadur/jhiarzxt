package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class M16StatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(M16Status.class);
        M16Status m16Status1 = new M16Status();
        m16Status1.setId(1L);
        M16Status m16Status2 = new M16Status();
        m16Status2.setId(m16Status1.getId());
        assertThat(m16Status1).isEqualTo(m16Status2);
        m16Status2.setId(2L);
        assertThat(m16Status1).isNotEqualTo(m16Status2);
        m16Status1.setId(null);
        assertThat(m16Status1).isNotEqualTo(m16Status2);
    }
}
