package com.master.core.controller;

import com.master.core.controller.interfaces.ICountryController;
import com.master.core.entity.CountryEntity;
import com.shared.core.service.*;
import com.shared.core.service.impl.*;
import com.shared.dto.external.master.CountryDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/countries")
public class CountryController implements ICountryController<CountryDto, Integer> {

    private final GenericFindAllService<CountryEntity, CountryDto, Integer> findAllService;
    private final GenericFindAllByListIdsService<CountryEntity, CountryDto, Integer> findAllByListIdsService;
    private final GenericFindByIdService<CountryEntity, CountryDto, Integer> findByIdService;
    private final GenericCreateService<CountryEntity, CountryDto, Integer> createService;
    private final GenericUpdateService<CountryEntity, CountryDto, Integer> updateService;
    private final GenericDeleteService<CountryEntity, CountryDto, Integer> deleteService;

    public CountryController(
            @Qualifier("findAllCountryImpl") GenericFindAllService<CountryEntity, CountryDto, Integer> findAllService,
            @Qualifier("findAllByListIdsCountryImpl") GenericFindAllByListIdsService<CountryEntity, CountryDto, Integer> findAllByListIdsService,
            @Qualifier("findByIdCountryImpl") GenericFindByIdService<CountryEntity, CountryDto, Integer> findByIdService,
            @Qualifier("createCountryImpl") GenericCreateService<CountryEntity, CountryDto, Integer> createService,
            @Qualifier("updateCountryImpl") GenericUpdateService<CountryEntity, CountryDto, Integer> updateService,
            @Qualifier("deleteCountryImpl") GenericDeleteService<CountryEntity, CountryDto, Integer> deleteService) {
        this.findAllService = findAllService;
        this.findAllByListIdsService = findAllByListIdsService;
        this.findByIdService = findByIdService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }

    @Override
    public FindAllService<CountryDto> getFindAllService() {
        return this.findAllService;
    }

    @Override
    public FindAllByListIdsService<CountryDto, Integer> getFindAllByListIdsService() {
        return this.findAllByListIdsService;
    }

    @Override
    public FindByIdService<CountryDto, Integer> getFindByIdService() {
        return this.findByIdService;
    }

    @Override
    public CreateService<CountryDto> getCreateService() {
        return this.createService;
    }

    @Override
    public UpdateService<CountryDto, Integer> getUpdateService() {
        return this.updateService;
    }

    @Override
    public DeleteService<CountryDto, Integer> getDeleteService() {
        return this.deleteService;
    }

}