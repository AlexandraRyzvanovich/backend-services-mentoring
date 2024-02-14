package com.epam.ryzvanovich.backendservicesmentoring.data;

import com.epam.ryzvanovich.backendservicesmentoring.data.UserRepository;
import com.epam.ryzvanovich.backendservicesmentoring.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserEntityRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUserCheckTheUserIsSavedReturnTrueTest() {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Alia");
        userEntity.setSurname("Ryzvanovich");
        userEntity.setAge(29);

        userRepository.save(userEntity);


        //when
        List<UserEntity> allUserEntities = userRepository.findAll();

        //then
        assertEquals(1, allUserEntities.size());
        assertEquals(userEntity.getAge(), allUserEntities.get(0).getAge());

    }
}