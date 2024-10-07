package com.catalogs.external.client;

import com.shared.utils.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Set;

@FeignClient(name = "languages",  url = "${feign.client.url}/api/languages") // configuration = JwtTokenPropagatingInterceptor.class
public interface LanguageClient {

    @GetMapping("/{id}")
    ResponseDto findById(@PathVariable Integer id);
    @PostMapping("/findAllByListIds")
    ResponseDto findAllByListIds(@RequestBody Set<Integer> listIds);

}