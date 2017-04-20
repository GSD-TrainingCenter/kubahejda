package cz.kubahejda.eet.model;

import com.fasterxml.jackson.annotation.JsonView;
import cz.kubahejda.eet.web.jsonview.Views;

/**
 * Created by Kuba on 30.3.2017.
 */
public class Receipt {
    @JsonView({Views.Public.class})
    private String date;
    @JsonView({Views.Public.class})
    private Long companyId;
    @JsonView({Views.Public.class})
    private String value;
    @JsonView({Views.Public.class})
    private String fik;
    @JsonView({Views.Public.class})
    private String bkp;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Receipt(Long companyId, String value, String fik, String date, String bkp) {

        this.date = date;
        this.companyId = companyId;
        this.value = value;
        this.fik = fik;
        this.bkp = bkp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFik() {
        return fik;
    }

    public void setFik(String fik) {
        this.fik = fik;
    }

    public String getBkp() {
        return bkp;
    }

    public void setBkp(String bkp) {
        this.bkp = bkp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Receipt{");
        sb.append("date='").append(date).append('\'');
        sb.append(", companyId='").append(companyId).append('\'');
        sb.append(", value='").append(value.toString()).append('\'');
        sb.append(", fik='").append(fik).append('\'');
        sb.append(", bkp='").append(bkp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
