package co.com.personalsoft.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildTypeMapperTest {

    private BuildTypeMapper buildTypeMapper;

    @BeforeEach
    public void setUp() {
        buildTypeMapper = new BuildTypeMapperImpl();
    }
}
