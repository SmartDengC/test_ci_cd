package com.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ScoreIntegrationDao {

    public int studentIsExist(@Param("NF") String year, @Param("SFDM") int provinceCode);

    public List<Map<String,Object>> getStudents(@Param("NF") String year, @Param("SFDM") int provinceCode);

    public Map<String,Float> getFourData(@Param("NF") String year, @Param("SFDM") int provinceCode, @Param("ID") int ID);

    public int setStudents(List<Map<String,Object>> students);

    public Map<String,Object> getThreeScore(@Param("Nf") String year,@Param("SFDM") int provinceCode);
}
