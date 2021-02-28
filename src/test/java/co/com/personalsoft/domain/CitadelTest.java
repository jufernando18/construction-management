package co.com.personalsoft.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitadelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Citadel.class);
        Citadel citadel1 = new Citadel();
        citadel1.setId(1L);
        Citadel citadel2 = new Citadel();
        citadel2.setId(citadel1.getId());
        assertThat(citadel1).isEqualTo(citadel2);
        citadel2.setId(2L);
        assertThat(citadel1).isNotEqualTo(citadel2);
        citadel1.setId(null);
        assertThat(citadel1).isNotEqualTo(citadel2);
    }
}
