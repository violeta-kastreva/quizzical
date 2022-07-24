package bg.softuni.quizzical.model.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

//@Entity
//@Table(name = "roles")
//public class Role extends BaseEntity implements GrantedAuthority {
//
//    private String authority;
//
//    public Role() {
//    }
//
//    public Role(String authority) {
//        this.authority = authority;
//    }
//
//    @Override
//    @Column(name = "authority", nullable = false)
//    public String getAuthority() {
//        return authority;
//    }
//
//    public void setAuthority(String authority) {
//        this.authority = authority;
//    }
//}

@Entity
@Table(name = "authorities")
public class Role extends BaseEntity implements GrantedAuthority{

    @Column(nullable = false, unique = true)
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {

    }



    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}