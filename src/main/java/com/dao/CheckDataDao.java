package com.dao;

public interface CheckDataDao {
    /**
     * check by data
     * @param
     * @return 0/1
     */
    String checkdata(String tablename);

    /**
     * checkSrt_U
     * @param year
     * @return  0/1
     */
    Object checkSRT_U(String year);

    /**
     * CheckSRT_ASS
     * @param year
     * @return 0/1
     */
    Object checkSRT_ASS(String year);

    /**
     * CheckCN
     * @param year
     * @return 0/1
     */
    Object checkCN(String year);

    /**
     * getSRT_U
     * @param
     * @return JSONArray
     */
    String getSRT_U();

    /**
     * getSRT_ASS
     * @param
     * @return JSONArray
     */
    String getSRT_ASS();

    /**
     * getCN
     * @param
     * @return JSONArray
     */
    String getCN();
}
