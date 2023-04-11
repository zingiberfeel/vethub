package com.potarski.vethub.repository;

import com.potarski.vethub.domain.animal.VetRecord;

import java.util.List;
import java.util.Optional;

public interface VetRecordRepo {

    Optional<VetRecord> findById(Long recordId);

    List<VetRecord> findAllByAnimalId(Long animalId);

    List<VetRecord> findAllByUserId(Long userId);

    void assignToAnimalById(Long recordId, Long animalId); // При создании vetrecord связываем его с животным

    void update(VetRecord vetRecord);

    void create(VetRecord vetRecord);

    void delete(Long id);
}
