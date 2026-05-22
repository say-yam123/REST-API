package com.pka.demo.reporting.service;

import com.pka.demo.reporting.model.ReportQueryResult;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportQueryService {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SqlFileLoader sqlFileLoader;

    public ReportQueryService(NamedParameterJdbcTemplate jdbcTemplate, SqlFileLoader sqlFileLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlFileLoader = sqlFileLoader;
    }

    public ReportQueryResult run(String sqlResource, Map<String, ?> parameters) {
        String sql = sqlFileLoader.load(sqlResource);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameters.forEach(parameterSource::addValue);

        List<LinkedHashMap<String, Object>> rows = jdbcTemplate.query(sql, parameterSource, (resultSet, rowNumber) -> {
            Map<String, Object> row = new ColumnMapRowMapper().mapRow(resultSet, rowNumber);
            return new LinkedHashMap<>(row);
        });
        return new ReportQueryResult(sql, rows);
    }
}
