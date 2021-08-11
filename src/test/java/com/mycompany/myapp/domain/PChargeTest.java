package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PCharge.class);
        PCharge pCharge1 = new PCharge();
        pCharge1.setId(1L);
        PCharge pCharge2 = new PCharge();
        pCharge2.setId(pCharge1.getId());
        assertThat(pCharge1).isEqualTo(pCharge2);
        pCharge2.setId(2L);
        assertThat(pCharge1).isNotEqualTo(pCharge2);
        pCharge1.setId(null);
        assertThat(pCharge1).isNotEqualTo(pCharge2);
    }
}
