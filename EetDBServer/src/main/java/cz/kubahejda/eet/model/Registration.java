package cz.kubahejda.eet.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.kubahejda.eet.web.jsonview.Views;

/**
 * Created by Kuba on 6.4.2017.
 */
public class Registration {
    @JsonView({Views.Public.class})
    int error;
    @JsonView({Views.Public.class})
    String email;

    public Registration(int error, String email) {
        this.error = error;
        this.email = email;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "error=" + error +
                ", email='" + email + '\'' +
                '}';
    }
}
