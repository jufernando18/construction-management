package co.com.personalsoft.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CitadelMapperTest {

    private CitadelMapper citadelMapper;

    @BeforeEach
    public void setUp() {
        citadelMapper = new CitadelMapperImpl();
    }
}
