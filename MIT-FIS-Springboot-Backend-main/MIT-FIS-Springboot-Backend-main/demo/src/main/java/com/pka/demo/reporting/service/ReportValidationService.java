package com.pka.demo.reporting.service;

import com.pka.demo.reporting.model.ReportValidationSummary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ReportValidationService {
    public ReportValidationSummary validate(List<LinkedHashMap<String, Object>> rows) {
        List<String> columns = rows.isEmpty() ? List.of() : new ArrayList<>(rows.get(0).keySet());
        Map<String, Long> nullCounts = new LinkedHashMap<>();
        columns.forEach(column -> nullCounts.put(column, 0L));

        long duplicates = rows.size() - new LinkedHashSet<>(rows).size();
        List<String> warnings = new ArrayList<>();

        for (LinkedHashMap<String, Object> row : rows) {
            for (String column : columns) {
                if (Objects.isNull(row.get(column))) {
                    nullCounts.compute(column, (key, value) -> value == null ? 1L : value + 1L);
                }
            }
        }

        if (rows.isEmpty()) {
            warnings.add("Report returned 0 rows.");
        }
        if (duplicates > 0) {
            warnings.add("Report contains " + duplicates + " duplicate rows.");
        }

        return new ReportValidationSummary(rows.size(), columns, nullCounts, duplicates, warnings);
    }
}
