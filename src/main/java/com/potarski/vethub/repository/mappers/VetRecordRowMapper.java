package com.potarski.vethub.repository.mappers;

import com.potarski.vethub.domain.animal.VetRecord;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VetRecordRowMapper {

    @SneakyThrows
    public static VetRecord mapRow(ResultSet resultSet){
        if (resultSet.next()){
            VetRecord vetRecord = new VetRecord();
            vetRecord.setId(resultSet.getLong("record_id"));
            vetRecord.setRecordName(resultSet.getString("record_name"));
            vetRecord.setRecordDescription(resultSet.getString("record_description"));
            return vetRecord;
        }
        return null;
    }
    @SneakyThrows
    public static List<VetRecord> mapRows(ResultSet resultSet) {
        List<VetRecord> vetRecords = new ArrayList<>();
        while (resultSet.next()){
            VetRecord vetRecord = new VetRecord();
            vetRecord.setId(resultSet.getLong("record_id"));
            vetRecord.setRecordName(resultSet.getString("record_name"));
            vetRecord.setRecordDescription(resultSet.getString("record_description"));
            vetRecords.add(vetRecord);
        }
        return vetRecords;
    }
}
