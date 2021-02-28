package co.com.personalsoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildTypeDTO.class);
        BuildTypeDTO buildTypeDTO1 = new BuildTypeDTO();
        buildTypeDTO1.setId(1L);
        BuildTypeDTO buildTypeDTO2 = new BuildTypeDTO();
        assertThat(buildTypeDTO1).isNotEqualTo(buildTypeDTO2);
        buildTypeDTO2.setId(buildTypeDTO1.getId());
        assertThat(buildTypeDTO1).isEqualTo(buildTypeDTO2);
        buildTypeDTO2.setId(2L);
        assertThat(buildTypeDTO1).isNotEqualTo(buildTypeDTO2);
        buildTypeDTO1.setId(null);
        assertThat(buildTypeDTO1).isNotEqualTo(buildTypeDTO2);
    }
}
