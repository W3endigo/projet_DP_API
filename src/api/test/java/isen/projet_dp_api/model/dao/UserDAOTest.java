package isen.projet_dp_api.model.dao;

import isen.projet_dp_api.utils.TestStrings;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
public class UserDAOTest {

    @Test
    void userDAOTest() {
        assertThat(new UserDAO(TestStrings.EMAIL_HAROLD).getEmail()).isEqualTo(TestStrings.EMAIL_HAROLD);
        assertThat(new UserDAO()).isNotNull();
    }
}
