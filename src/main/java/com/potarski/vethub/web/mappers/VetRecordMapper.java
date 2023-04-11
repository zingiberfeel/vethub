package com.potarski.vethub.web.mappers;

import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.web.dto.vetrecord.VetRecordDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VetRecordMapper {
    VetRecordDto toDto(VetRecord vetRecord);

    List<VetRecordDto> toDto(List<VetRecord> vetRecords);

    VetRecord toEntity(VetRecordDto vetRecordDto);
}
