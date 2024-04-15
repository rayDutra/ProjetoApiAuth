package com.nttdata.authentication.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8080", name= "validation")
public interface ValidationsClient {

    @GetMapping("/api/users/search/{email}")
    User checkEmail(@PathVariable String email);

}
