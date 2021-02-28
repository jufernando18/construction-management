package co.com.personalsoft.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequisitionMapperTest {

    private RequisitionMapper requisitionMapper;

    @BeforeEach
    public void setUp() {
        requisitionMapper = new RequisitionMapperImpl();
    }
}
