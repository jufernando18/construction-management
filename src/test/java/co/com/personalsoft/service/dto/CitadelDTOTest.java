package co.com.personalsoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitadelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitadelDTO.class);
        CitadelDTO citadelDTO1 = new CitadelDTO();
        citadelDTO1.setId(1L);
        CitadelDTO citadelDTO2 = new CitadelDTO();
        assertThat(citadelDTO1).isNotEqualTo(citadelDTO2);
        citadelDTO2.setId(citadelDTO1.getId());
        assertThat(citadelDTO1).isEqualTo(citadelDTO2);
        citadelDTO2.setId(2L);
        assertThat(citadelDTO1).isNotEqualTo(citadelDTO2);
        citadelDTO1.setId(null);
        assertThat(citadelDTO1).isNotEqualTo(citadelDTO2);
    }
}
