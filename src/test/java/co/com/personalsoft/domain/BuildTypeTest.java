package co.com.personalsoft.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.com.personalsoft.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildType.class);
        BuildType buildType1 = new BuildType();
        buildType1.setId(1L);
        BuildType buildType2 = new BuildType();
        buildType2.setId(buildType1.getId());
        assertThat(buildType1).isEqualTo(buildType2);
        buildType2.setId(2L);
        assertThat(buildType1).isNotEqualTo(buildType2);
        buildType1.setId(null);
        assertThat(buildType1).isNotEqualTo(buildType2);
    }
}
