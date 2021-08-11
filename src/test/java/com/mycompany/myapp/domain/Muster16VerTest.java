package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Muster16VerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Muster16Ver.class);
        Muster16Ver muster16Ver1 = new Muster16Ver();
        muster16Ver1.setId(1L);
        Muster16Ver muster16Ver2 = new Muster16Ver();
        muster16Ver2.setId(muster16Ver1.getId());
        assertThat(muster16Ver1).isEqualTo(muster16Ver2);
        muster16Ver2.setId(2L);
        assertThat(muster16Ver1).isNotEqualTo(muster16Ver2);
        muster16Ver1.setId(null);
        assertThat(muster16Ver1).isNotEqualTo(muster16Ver2);
    }
}
