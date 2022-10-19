package jp.co.sss.sportsCenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * User
 */

@Entity
@Table(name = "SPORTSCENTER_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sportscenter_user_gen")
    @SequenceGenerator(name = "seq_sportscenter_user_gen", sequenceName = "SEQ_SPORTSCENTER_USER", allocationSize = 1)
    private Integer id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private Integer post;

    @Column
    private String domicile;

    @Column
    private Integer authority;

    public User() {
    }

    public User(Integer id, String name, String password, String email, String phoneNumber, Integer post,
            String domicile, Integer authority) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.post = post;
        this.domicile = domicile;
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

}