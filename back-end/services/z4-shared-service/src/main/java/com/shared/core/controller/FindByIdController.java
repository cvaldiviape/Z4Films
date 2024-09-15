package com.shared.core.controller;

import com.shared.core.service.FindByIdService;
import com.shared.enums.ControllerMessageEnum;
import com.shared.utils.response.ResponseDto;
import com.shared.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface FindByIdController<DTO, ID> {

    FindByIdService<DTO, ID> getFindByIdService();

    @GetMapping("/{id}")
    default ResponseEntity<ResponseDto> findById(@PathVariable ID id) {
        DTO result = this.getFindByIdService().findById(id);
        ResponseDto response = ResponseUtil.ok(ControllerMessageEnum.FIND_BY_IO, result);
        return ResponseEntity.ok(response);
    }

}