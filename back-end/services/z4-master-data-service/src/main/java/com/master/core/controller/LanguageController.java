package com.master.core.controller;

import com.master.core.controller.interfaces.ILanguageController;
import com.master.core.entity.LanguageEntity;
import com.shared.core.service.*;
import com.shared.core.service.impl.*;
import com.shared.dto.external.master.LanguageDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/languages")
public class LanguageController implements ILanguageController<LanguageDto, Integer> {

    private final GenericFindByIdService<LanguageEntity, LanguageDto, Integer> findByIdService;
    private final GenericFindAllService<LanguageEntity, LanguageDto, Integer> findAllService;
    private final GenericCreateService<LanguageEntity, LanguageDto, Integer> createService;
    private final GenericUpdateService<LanguageEntity, LanguageDto, Integer> updateService;
    private final GenericDeleteService<LanguageEntity, LanguageDto, Integer> deleteService;

    public LanguageController(
            @Qualifier("findAllLanguageImpl") GenericFindAllService<LanguageEntity, LanguageDto, Integer> findAllService,
            @Qualifier("findByIdLanguageImpl") GenericFindByIdService<LanguageEntity, LanguageDto, Integer> findByIdService,
            @Qualifier("createLanguageImpl") GenericCreateService<LanguageEntity, LanguageDto, Integer> createService,
            @Qualifier("updateLanguageImpl") GenericUpdateService<LanguageEntity, LanguageDto, Integer> updateService,
            @Qualifier("deleteLanguageImpl") GenericDeleteService<LanguageEntity, LanguageDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public FindAllService<LanguageDto> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public FindByIdService<LanguageDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public CreateService<LanguageDto> getCreateService() {
        return this.createService;
    }

    @Override
    public UpdateService<LanguageDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public DeleteService<LanguageDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}