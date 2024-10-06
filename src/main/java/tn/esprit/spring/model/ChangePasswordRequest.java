package tn.esprit.spring.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
public class ChangePasswordRequest {

    private String oldPass;
    private String newPass;
    private String email;

    public String getOldPass() {
        return oldPass != null ? oldPass : "";
    }

}
