package co.com.personalsoft.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildOrder.class);
        BuildOrder buildOrder1 = new BuildOrder();
        buildOrder1.setId(1L);
        BuildOrder buildOrder2 = new BuildOrder();
        buildOrder2.setId(buildOrder1.getId());
        assertThat(buildOrder1).isEqualTo(buildOrder2);
        buildOrder2.setId(2L);
        assertThat(buildOrder1).isNotEqualTo(buildOrder2);
        buildOrder1.setId(null);
        assertThat(buildOrder1).isNotEqualTo(buildOrder2);
    }
}
