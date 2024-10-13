package com.studios.core.controller;

import com.studios.core.controller.interfaces.IStudioController;
import com.studios.core.entity.StudioEntity;
import com.shared.core.service.*;
import com.shared.core.service.impl.*;
import com.shared.dto.external.studio.StudioDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/studios")
public class StudioController implements IStudioController<StudioDto, Integer> {

    private final GenericFindAllService<StudioEntity, StudioDto, Integer> findAllService;
    private final GenericFindAllByListIdsService<StudioEntity, StudioDto, Integer> findAllByListIdsService;
    private final GenericFindByIdService<StudioEntity, StudioDto, Integer> findByIdService;
    private final GenericCreateService<StudioEntity, StudioDto, Integer> createService;
    private final GenericUpdateService<StudioEntity, StudioDto, Integer> updateService;
    private final GenericDeleteService<StudioEntity, StudioDto, Integer> deleteService;

    public StudioController(
            @Qualifier("findAllStudioImpl") GenericFindAllService<StudioEntity, StudioDto, Integer> findAllService,
            @Qualifier("findAllByListIdsStudioImpl") GenericFindAllByListIdsService<StudioEntity, StudioDto, Integer> findAllByListIdsService,
            @Qualifier("findByIdStudioImpl") GenericFindByIdService<StudioEntity, StudioDto, Integer> findByIdService,
            @Qualifier("createStudioImpl") GenericCreateService<StudioEntity, StudioDto, Integer> createService,
            @Qualifier("updateStudioImpl") GenericUpdateService<StudioEntity, StudioDto, Integer> updateService,
            @Qualifier("deleteStudioImpl") GenericDeleteService<StudioEntity, StudioDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findAllByListIdsService = findAllByListIdsService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public FindAllService<StudioDto> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public FindAllByListIdsService<StudioDto, Integer> getFindAllByListIdsService() {
        return this.findAllByListIdsService;
    }

    @Override
    public FindByIdService<StudioDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public CreateService<StudioDto> getCreateService() {
        return this.createService;
    }

    @Override
    public UpdateService<StudioDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public DeleteService<StudioDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}