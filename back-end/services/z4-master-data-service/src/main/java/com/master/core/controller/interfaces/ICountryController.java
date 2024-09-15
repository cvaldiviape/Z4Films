package com.master.core.controller.interfaces;

import com.shared.core.controller.*;

public interface ICountryController <DTO, ID> extends
        FindByIdController<DTO, ID>,
        FindAllController<DTO>,
        CreateController<DTO>,
        UpdateController<DTO, ID>,
        DeleteController<DTO, ID> {
}