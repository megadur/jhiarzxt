package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PWirkstoffTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PWirkstoff.class);
        PWirkstoff pWirkstoff1 = new PWirkstoff();
        pWirkstoff1.setId(1L);
        PWirkstoff pWirkstoff2 = new PWirkstoff();
        pWirkstoff2.setId(pWirkstoff1.getId());
        assertThat(pWirkstoff1).isEqualTo(pWirkstoff2);
        pWirkstoff2.setId(2L);
        assertThat(pWirkstoff1).isNotEqualTo(pWirkstoff2);
        pWirkstoff1.setId(null);
        assertThat(pWirkstoff1).isNotEqualTo(pWirkstoff2);
    }
}
