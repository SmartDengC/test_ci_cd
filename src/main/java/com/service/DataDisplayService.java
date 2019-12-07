package com.service;

import java.util.List;
import java.util.Map;

public interface DataShowService {

    public Map<String,Object> getStudentsTotal();

    public Map<String,Object> getStudentRatio();

    public List<Map<String,Integer>> getStudentsByProvince();

}
