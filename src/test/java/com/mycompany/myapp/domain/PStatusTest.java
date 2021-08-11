package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PStatus.class);
        PStatus pStatus1 = new PStatus();
        pStatus1.setId(1L);
        PStatus pStatus2 = new PStatus();
        pStatus2.setId(pStatus1.getId());
        assertThat(pStatus1).isEqualTo(pStatus2);
        pStatus2.setId(2L);
        assertThat(pStatus1).isNotEqualTo(pStatus2);
        pStatus1.setId(null);
        assertThat(pStatus1).isNotEqualTo(pStatus2);
    }
}
