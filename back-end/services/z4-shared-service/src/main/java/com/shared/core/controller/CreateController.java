package com.shared.core.controller;

import com.shared.core.service.CreateService;
import com.shared.enums.ControllerMessageEnum;
import com.shared.utils.response.ResponseDto;
import com.shared.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreateController<DTO> {

    CreateService<DTO> getCreateService();

    @PostMapping
    default ResponseEntity<ResponseDto> create(@RequestBody DTO dto) {
        DTO result = this.getCreateService().create(dto);
        ResponseDto response = ResponseUtil.ok(ControllerMessageEnum.CREATE, result);
        return ResponseEntity.ok(response);
    }

}