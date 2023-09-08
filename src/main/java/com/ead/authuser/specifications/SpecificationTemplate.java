package com.ead.authuser.specifications;

import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = EqualIgnoreCase.class),
            @Spec(path = "userStatus", spec = EqualIgnoreCase.class),
            @Spec(path = "email", spec = LikeIgnoreCase.class),
            @Spec(path = "fullName", spec = LikeIgnoreCase.class)
    })
    public interface UserSpec extends Specification<UserModel> {}
}
