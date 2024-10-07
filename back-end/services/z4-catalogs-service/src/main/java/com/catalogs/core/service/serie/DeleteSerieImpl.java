package com.catalogs.core.service.serie;

import com.catalogs.core.entity.SerieEntity;
import com.catalogs.core.entity.mapper.SerieMapper;
import com.catalogs.core.repository.SerieRepository;
import com.shared.core.service.DeleteService;
import com.shared.dto.external.catalog.SerieDto;
import com.shared.enums.ValueEnum;
import com.shared.utils.ValidateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@AllArgsConstructor
@Service("deleteSerieImpl")
public class DeleteSerieImpl implements DeleteService<SerieDto, Integer> {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;

    @Override
    public SerieDto delete(Integer idSerie) {
        SerieEntity serieEntity = this.findSerieById(idSerie);
        this.serieRepository.delete(serieEntity);
        return this.serieMapper.toDto(serieEntity);
    }

    private SerieEntity findSerieById(Integer idSerie) {
        return this.serieRepository.findById(idSerie)
                .orElseThrow(() -> ValidateUtil.throwNotFoundException(ValueEnum.SERIE.getValue()));
    }

}