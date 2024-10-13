package com.studios.external.client;

import com.shared.utils.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;

@FeignClient(name = "countries",  url = "${feign.client.url}/api/countries") // configuration = JwtTokenPropagatingInterceptor.class
public interface CountryClient {

    @GetMapping("/{id}")
    ResponseDto findById(@PathVariable Integer id);
    @PostMapping("/findAllByListIds")
    ResponseDto findAllByListIds(@RequestBody Set<Integer> listIds);

}