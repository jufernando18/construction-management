package co.com.personalsoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildOrderDTO.class);
        BuildOrderDTO buildOrderDTO1 = new BuildOrderDTO();
        buildOrderDTO1.setId(1L);
        BuildOrderDTO buildOrderDTO2 = new BuildOrderDTO();
        assertThat(buildOrderDTO1).isNotEqualTo(buildOrderDTO2);
        buildOrderDTO2.setId(buildOrderDTO1.getId());
        assertThat(buildOrderDTO1).isEqualTo(buildOrderDTO2);
        buildOrderDTO2.setId(2L);
        assertThat(buildOrderDTO1).isNotEqualTo(buildOrderDTO2);
        buildOrderDTO1.setId(null);
        assertThat(buildOrderDTO1).isNotEqualTo(buildOrderDTO2);
    }
}
