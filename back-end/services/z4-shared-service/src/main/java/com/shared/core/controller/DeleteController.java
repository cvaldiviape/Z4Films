package com.shared.core.controller;

import com.shared.core.service.DeleteService;
import com.shared.enums.ControllerMessageEnum;
import com.shared.utils.response.ResponseDto;
import com.shared.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeleteController<DTO, ID> {

    DeleteService<DTO, ID> getDeleteService();

    @DeleteMapping("/{id}")
    default ResponseEntity<ResponseDto> delete(@PathVariable ID id) {
        DTO result = this.getDeleteService().delete(id);
        ResponseDto response = ResponseUtil.ok(ControllerMessageEnum.DELETE, result);
        return ResponseEntity.ok(response);
    }

}