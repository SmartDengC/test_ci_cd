package com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ScoreIntegrationService {

    public int studentIsExist(String year, int provinceCode);

    public List<Map<String,Object>> getStudents(String year, int provinceCode);

    public int integrationStudents(List<Map<String,Object>> students, String year, int provinceCode);

    public String getThreeScore(String year, int provinceCode) throws JsonProcessingException;

}
