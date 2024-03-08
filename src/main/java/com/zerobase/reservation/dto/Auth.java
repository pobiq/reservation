package com.zerobase.reservation.dto;

import com.zerobase.reservation.entity.MemberEntity;
import lombok.Data;

import java.util.List;

public class Auth {

    @Data
    public static class SignIn {
        private String username;
        private String password;
        private String phone;
    }

    @Data
    public static class SignUp {
        private String username;
        private String password;
        private String phone;
        private List<String> roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                            .username(this.username)
                            .password(this.password)
                            .roles(this.roles)
                            .phone(this.phone)
                            .build();
        }
    }
}
