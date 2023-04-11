package com.potarski.vethub.service;

import com.potarski.vethub.domain.animal.VetRecord;

import java.util.List;

public interface VetRecordService {
    VetRecord getById(Long id);

    List<VetRecord> getAllByAnimalId(Long animalId);

    List<VetRecord> getAllByUserId(Long userId);

    VetRecord update(VetRecord vetRecord);

    VetRecord create(VetRecord vetRecord, Long animalId);

    void delete(Long id);
}
