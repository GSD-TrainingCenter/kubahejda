package cz.kubahejda.eet.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.kubahejda.eet.web.jsonview.Views;

/**
 * Created by Kuba on 23.3.2017.
 */
public class User {
    @JsonView({Views.Public.class})
    private String id;
    @JsonView({Views.Public.class})
    private String username;
    @JsonView({Views.Public.class})
    private String password;
    @JsonView({Views.Public.class})
    private String companyName;
    @JsonView({Views.Public.class})
    private Long companyId;
    @JsonView({Views.Public.class})
    private String vatId;

    public User () {}

    public User(String id, String username, String password, String companyName, Long companyId, String vatId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.companyId = companyId;
        this.vatId = vatId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append(", companyID='").append(companyId.toString()).append('\'');
        sb.append(", vatID='").append(vatId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
