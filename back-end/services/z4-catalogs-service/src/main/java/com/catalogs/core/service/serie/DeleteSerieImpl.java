package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.shared.core.service.DeleteService;
import com.shared.core.service.impl.GenericDeleteService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Transactional
@AllArgsConstructor
@Service("deleteSerieImpl")
public class DeleteSerieImpl extends GenericDeleteService<SerieEntity, SerieDto, UUID> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;

    @Override
    public JpaRepository<SerieEntity, UUID> getJpaRepository() {
        return this.serieRepository;
    }

    @Override
    public SerieDto toDto(SerieEntity serieEntity) {
        return this.serieMapper.toDto(serieEntity);
    }

    @Override
    public SerieEntity findEntityById(UUID serieId) {
        return this.serieRepository.findById(serieId)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.SERIE.getValue()));
    }

}