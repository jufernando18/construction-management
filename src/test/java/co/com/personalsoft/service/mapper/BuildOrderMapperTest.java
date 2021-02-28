package co.com.personalsoft.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildOrderMapperTest {

    private BuildOrderMapper buildOrderMapper;

    @BeforeEach
    public void setUp() {
        buildOrderMapper = new BuildOrderMapperImpl();
    }
}
