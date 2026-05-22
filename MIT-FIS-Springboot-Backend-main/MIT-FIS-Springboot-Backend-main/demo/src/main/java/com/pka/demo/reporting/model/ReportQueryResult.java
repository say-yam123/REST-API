package com.pka.demo.reporting.model;

import java.util.LinkedHashMap;
import java.util.List;

public class ReportQueryResult {
    private final String sqlText;
    private final List<LinkedHashMap<String, Object>> rows;

    public ReportQueryResult(String sqlText, List<LinkedHashMap<String, Object>> rows) {
        this.sqlText = sqlText;
        this.rows = rows;
    }

    public String getSqlText() {
        return sqlText;
    }

    public List<LinkedHashMap<String, Object>> getRows() {
        return rows;
    }
}
