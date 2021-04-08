package ma.youcode.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(
                name = "findUser",
                query = "from Users e where e.email = :mail"
        ),
})
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "pwd")
    @JsonIgnore
    private String pwd;
    @Column(name = "type")
    private String type;
    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean status;

    public Users() {
    }

    public Users( String fullName, String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
        this.fullName = fullName;
    }

    public Users(String fullName, String email, String pwd, String type) {
        this.fullName = fullName;
        this.email = email;
        this.pwd = pwd;
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(fullName, users.fullName) && Objects.equals(email, users.email) && Objects.equals(pwd, users.pwd) && Objects.equals(type, users.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, pwd, type);
    }


}
