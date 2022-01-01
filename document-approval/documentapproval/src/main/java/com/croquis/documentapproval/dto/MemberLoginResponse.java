package com.croquis.documentapproval.dto;

import com.croquis.documentapproval.domain.Authority;
import com.croquis.documentapproval.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberLoginResponse {

    private String email;
    private String password;
    private Authority authority;

    public MemberLoginResponse(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public static MemberLoginResponse of(Member member) {
        return new MemberLoginResponse(
                member.getEmail(),
                member.getPassword(),
                member.getAuthority());
    }

}
