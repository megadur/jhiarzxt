package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EStatus.class);
        EStatus eStatus1 = new EStatus();
        eStatus1.setId(1L);
        EStatus eStatus2 = new EStatus();
        eStatus2.setId(eStatus1.getId());
        assertThat(eStatus1).isEqualTo(eStatus2);
        eStatus2.setId(2L);
        assertThat(eStatus1).isNotEqualTo(eStatus2);
        eStatus1.setId(null);
        assertThat(eStatus1).isNotEqualTo(eStatus2);
    }
}
