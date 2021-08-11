package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Muster16Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Muster16.class);
        Muster16 muster161 = new Muster16();
        muster161.setId(1L);
        Muster16 muster162 = new Muster16();
        muster162.setId(muster161.getId());
        assertThat(muster161).isEqualTo(muster162);
        muster162.setId(2L);
        assertThat(muster161).isNotEqualTo(muster162);
        muster161.setId(null);
        assertThat(muster161).isNotEqualTo(muster162);
    }
}
