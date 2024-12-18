package com.shared.core.controller;

import com.shared.core.service.FindAllService;
import com.shared.enums.ControllerMessageEnum;
import com.shared.utils.response.ResponseDto;
import com.shared.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

public interface FindAllController<DTO> {

    FindAllService<DTO> getFindAllService();

    @GetMapping
    default ResponseEntity<ResponseDto> findAll() {
        List<DTO> result = this.getFindAllService().findAll();
        ResponseDto response = ResponseUtil.ok(ControllerMessageEnum.FIND_ALL, result);
        return ResponseEntity.ok(response);
    }

}