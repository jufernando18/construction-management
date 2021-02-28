package co.com.personalsoft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequisitionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequisitionDTO.class);
        RequisitionDTO requisitionDTO1 = new RequisitionDTO();
        requisitionDTO1.setId(1L);
        RequisitionDTO requisitionDTO2 = new RequisitionDTO();
        assertThat(requisitionDTO1).isNotEqualTo(requisitionDTO2);
        requisitionDTO2.setId(requisitionDTO1.getId());
        assertThat(requisitionDTO1).isEqualTo(requisitionDTO2);
        requisitionDTO2.setId(2L);
        assertThat(requisitionDTO1).isNotEqualTo(requisitionDTO2);
        requisitionDTO1.setId(null);
        assertThat(requisitionDTO1).isNotEqualTo(requisitionDTO2);
    }
}
