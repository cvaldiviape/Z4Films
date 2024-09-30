package com.master.core.controller.interfaces;

import com.shared.core.controller.*;

public interface ICountryController <DTO, ID> extends
        FindAllController<DTO>,
        FindAllByListIdsController<DTO, ID>,
        FindByIdController<DTO, ID>,
        CreateController<DTO>,
        UpdateController<DTO, ID>,
        DeleteController<DTO, ID> {
}