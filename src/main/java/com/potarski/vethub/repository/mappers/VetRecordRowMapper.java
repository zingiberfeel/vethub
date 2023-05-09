package com.potarski.vethub.repository.mappers;

import com.potarski.vethub.domain.animal.VetRecord;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VetRecordRowMapper {

    @SneakyThrows
    public static VetRecord mapRow(ResultSet resultSet) {

        if (resultSet.next()) {
            VetRecord vetRecord = new VetRecord();
            vetRecord.setId(resultSet.getLong("record_id"));
            vetRecord.setTitle(resultSet.getString("record_name"));
            vetRecord.setDescription(resultSet.getString("record_description"));
            Timestamp timestamp = resultSet.getTimestamp("record_date");
            if (timestamp != null) {
                vetRecord.setTimestamp(timestamp.toLocalDateTime().toLocalDate());
            }
            return vetRecord;
        }
        return null;
    }

    @SneakyThrows
    public static List<VetRecord> mapRows(ResultSet resultSet) {
        List<VetRecord> vetRecords = new ArrayList<>();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            long vetRecordId = resultSet.getLong("record_id");
            if (vetRecordId != 0) {
                VetRecord vetRecord = new VetRecord();
                vetRecord.setId(vetRecordId);
                vetRecord.setTitle(resultSet.getString("record_name"));
                vetRecord.setDescription(resultSet.getString("record_description"));
                Timestamp timestamp = resultSet.getTimestamp("record_date");
                if (timestamp != null) {
                    vetRecord.setTimestamp(timestamp.toLocalDateTime().toLocalDate());
                }
                vetRecords.add(vetRecord);
            }}
            return vetRecords.stream()
                    .distinct()
                    .collect(Collectors.toList());
        }
    }
