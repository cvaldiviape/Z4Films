package com.shared.core.controller;

import com.shared.core.service.UpdateService;
import com.shared.enums.ControllerMessageEnum;
import com.shared.utils.response.ResponseDto;
import com.shared.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateController<DTO, ID> {

    UpdateService<DTO, ID> getUpdateService();

    @PutMapping("/{id}")
    default ResponseEntity<ResponseDto> update(@PathVariable ID id, @RequestBody DTO dto ) {
        DTO result = this.getUpdateService().update(id, dto);
        ResponseDto response = ResponseUtil.ok(ControllerMessageEnum.UPDATE, result);
        return ResponseEntity.ok(response);
    }

}