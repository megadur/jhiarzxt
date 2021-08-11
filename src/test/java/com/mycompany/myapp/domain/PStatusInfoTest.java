package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PStatusInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PStatusInfo.class);
        PStatusInfo pStatusInfo1 = new PStatusInfo();
        pStatusInfo1.setId(1L);
        PStatusInfo pStatusInfo2 = new PStatusInfo();
        pStatusInfo2.setId(pStatusInfo1.getId());
        assertThat(pStatusInfo1).isEqualTo(pStatusInfo2);
        pStatusInfo2.setId(2L);
        assertThat(pStatusInfo1).isNotEqualTo(pStatusInfo2);
        pStatusInfo1.setId(null);
        assertThat(pStatusInfo1).isNotEqualTo(pStatusInfo2);
    }
}
