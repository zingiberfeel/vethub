package com.potarski.vethub.service.impl;

import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.domain.exception.ResourceNotFoundException;
import com.potarski.vethub.repository.VetRecordRepo;
import com.potarski.vethub.service.VetRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VetRecordServiceImpl implements VetRecordService {
    private final VetRecordRepo vetRecordRepo;

    @Transactional(readOnly = true)
    @Override
    public VetRecord getById(Long id) {
        return vetRecordRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record with this id doesn't exist")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<VetRecord> getAllByAnimalId(Long animalId) {
        return vetRecordRepo.findAllByAnimalId(animalId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VetRecord> getAllByUserId(Long id) {
        return vetRecordRepo.findAllByUserId(id);
    }

    @Transactional
    @Override
    public VetRecord update(VetRecord vetRecord) {
        vetRecordRepo.update(vetRecord);
        return vetRecord;
    }

    @Transactional
    @Override
    public VetRecord create(VetRecord vetRecord, Long animalId) {
        vetRecordRepo.create(vetRecord);
        vetRecordRepo.assignToAnimalById(vetRecord.getId(), animalId);
        return vetRecord;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        vetRecordRepo.delete(id);
    }
}
