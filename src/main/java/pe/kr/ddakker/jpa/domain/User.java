package pe.kr.ddakker.jpa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;




@Entity
public class User implements Serializable {
    @Id
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NM")
    private String name;

    @Column(name = "USER_AGE")
    private Integer age;

    @Temporal(TemporalType.DATE)
    @Column(name = "REG_DT")
    private Date regDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}