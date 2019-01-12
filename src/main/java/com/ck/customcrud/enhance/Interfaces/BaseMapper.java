package com.ck.customcrud.enhance.Interfaces;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * @author 陈坤
 * @serial 2019/1/8
 */
public interface BaseMapper<T> {

    /**
     * 根据id查询单个
     *
     * @param map map
     * @return T
     */
    // and IS_DELETE = 1
    @Select("SELECT ${ckMap.TABLE_COLUMN} FROM ${ckMap.TABLE_NAME} WHERE ID = #{ckMap.TABLE_ID_VALUE} ")
    T selectById(@Param("ckMap") Map<String, Object> map);

    /**
     * 根据id删除单个
     *
     * @param map map
     * @return boolean
     */
    @Delete("DELETE FROM ${ckMap.TABLE_NAME} WHERE ID = #{ckMap.TABLE_ID_VALUE}")
    boolean deleteById(@Param("ckMap") Map<String, Object> map);

    /**
     * 插入数据
     *
     * @param map map
     * @return boolean
     */
    @Insert("<script>" +
            "insert into ${ckMap.TABLE_NAME}" +
            "   <foreach collection='ckMap.ENTITY_MAP.keys' index='index' item='item' open='(' separator=',' close=')'>" +
            "       ${item}" +
            "   </foreach>" +
            "VALUES" +
            "   <foreach collection='ckMap.ENTITY_MAP' index='index' item='item1' open='(' separator=',' close=')'>" +
            "       #{item1}" +
            "   </foreach>" +
            "</script>")
    boolean insert(@Param("ckMap") Map<String, Object> map);


    /**
     * 根据id更新数据
     *
     * @param map map
     * @return boolean
     */
    @Update("<script>" +
            "UPDATE  ${ckMap.TABLE_NAME}" +
            " SET " +
            "   <foreach collection='ckMap.ENTITY_MAP.keys' index='index' item='item' open='' separator=',' close=''>" +
            "       ${item} = #{ckMap.ENTITY_MAP[${item}]}" +
            "   </foreach>" +
            " WHERE ID = ${ckMap.TABLE_ID_VALUE}" +
            "</script>")
    boolean updateById(@Param("ckMap") Map<String, Object> map);

//--------------------------------------------------------------------------------------------------------------------------


//    @Select("")
//    List<T> selectList();

//    @Select("")
//    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> var1);

//    @Delete("")
//    int deleteBatchIds(@Param("coll") Collection<? extends Serializable> var1);

}
