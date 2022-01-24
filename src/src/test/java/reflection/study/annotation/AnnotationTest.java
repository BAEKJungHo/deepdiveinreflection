package reflection.study.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reflection.study.annotation.code.Dao;
import reflection.study.annotation.code.Dto;
import reflection.study.annotation.code.Team;
import reflection.study.annotation.code.User;

@Slf4j
class AnnotationTest {

    @Test
    void inheritedTest() {
        Class<User> userClass = User.class;
        log.info("User Dto Annotation = {}", userClass.getAnnotation(Dto.class));
        log.info("User Dao Annotation = {}", userClass.getAnnotation(Dao.class));

        Class<Team> teamClass = Team.class;
        log.info("Team Dto Annotation = {}", teamClass.getAnnotation(Dto.class));
        log.info("Team Dao Annotation = {}", teamClass.getAnnotation(Dao.class));
    }
}
