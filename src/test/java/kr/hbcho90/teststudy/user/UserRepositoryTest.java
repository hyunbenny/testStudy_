package kr.hbcho90.teststudy.user;

import kr.hbcho90.teststudy.fixtures.UserFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/sql/user-test-data.sql"})
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    void insert() {
        // given
        UserEntity userEntity = UserFixtures.createUserEntity();
        long beforeTotal = userRepository.count();

        // when
        UserEntity saveEntity = userRepository.save(userEntity);

        // then
        assertThat(saveEntity).isNotNull();
        assertThat(saveEntity.getId()).isEqualTo(userEntity.getId());
        assertThat(saveEntity.getUserId()).isEqualTo(userEntity.getUserId());
        assertThat(userRepository.count()).isEqualTo(beforeTotal + 1);

    }

    @Test
    void findById() {
        // given
        String userId = "649466541960560";

        // when
        UserEntity userEntity = userRepository.findById(userId).get();

        // then
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getUserId()).isEqualTo("test1");
        assertThat(userEntity.getId()).isEqualTo("649466541960560");
    }

    @Test
    void findByUserId() {
        // given
        String userId = "test1";

        // when
        UserEntity userEntity = userRepository.findByUserId(userId).get();

        // then
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getUserId()).isEqualTo("test1");
        assertThat(userEntity.getId()).isEqualTo("649466541960560");
    }

    @Test
    void deleteById_Fail() {
        // given
        long total = userRepository.count();
        String userId = "649466541960560";

        // when
        userRepository.deleteById(userId);

        // then
        assertThat(userRepository.findById(userId)).isEmpty();
        assertThat(userRepository.count()).isEqualTo(total - 1);
    }


}