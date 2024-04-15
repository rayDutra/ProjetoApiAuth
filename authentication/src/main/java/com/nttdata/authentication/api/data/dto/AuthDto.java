package com.nttdata.authentication.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthDto {

        private Long id;

        private String email;

        private String senha;

        public AuthDto() {

        }

    }
