package com.potarski.vethub.web.controller;


import com.potarski.vethub.domain.animal.Animal;
import com.potarski.vethub.domain.animal.VetRecord;
import com.potarski.vethub.service.AnimalService;
import com.potarski.vethub.service.VetRecordService;
import com.potarski.vethub.web.dto.animal.AnimalDto;
import com.potarski.vethub.web.dto.validation.OnUpdate;
import com.potarski.vethub.web.dto.vetrecord.VetRecordDto;
import com.potarski.vethub.web.mappers.AnimalMapper;
import com.potarski.vethub.web.mappers.VetRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
@Validated
public class VetRecordController {
    private final VetRecordService vetRecordService;
    private final AnimalService animalService;

    private final VetRecordMapper vetRecordMapper;
    private final AnimalMapper animalMapper;

    @GetMapping("/{id}")
    public VetRecordDto getById(@PathVariable Long id){
        VetRecord vetRecord = vetRecordService.getById(id);
        return vetRecordMapper.toDto(vetRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        vetRecordService.delete(id);
    }

    @PutMapping
    public VetRecordDto update(@Validated(OnUpdate.class) @RequestBody VetRecordDto vetRecordDto){
        VetRecord vetRecord = vetRecordMapper.toEntity(vetRecordDto);
        VetRecord updatedVetRecord = vetRecordService.update(vetRecord);
        return vetRecordMapper.toDto(updatedVetRecord);
    }

}
