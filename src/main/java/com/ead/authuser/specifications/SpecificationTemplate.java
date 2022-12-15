package com.ead.authuser.specifications;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "username", spec = Like.class),
            @Spec(path = "cpf", spec = Like.class),
            @Spec(path = "fullName", spec = LikeIgnoreCase.class)
    })
    public interface UserSpec extends Specification<UserModel> {}

    public static Specification<UserModel> userCourseId(final UUID courseId){
        return (root, query, criteriaBuilder) -> {

            query.distinct(true); //Aqui defino que vou querer apenas dados que não se repetem
            Join<UserModel, UserCourseModel> userProd = root.join("usersCourses"); //Nesta linha faço um simples join, passando as duas entidades e qual relação entre elas
            //EX: SELECT * ALL TB_USERS
            //JOIN TB_USERS_COURSES ON COURSE_ID = COURSE_ID
            return criteriaBuilder.equal(userProd.get("courseId"), courseId);
         };
    }
}
