package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
    @Insert("insert into employee(name, username, password,phone,sex,id_number, create_time, update_time,create_user,update_user) " +
            "values (#{employee.name}, #{employee.username}, #{employee.password},#{employee.phone},#{employee.sex}, #{employee.idNumber},#{employee.createTime},#{employee.updateTime},#{employee.createUser},#{employee.updateUser})")
    void insertEmployee(@Param("employee") Employee employee);

    Page<Employee> selectLimit(EmployeePageQueryDTO pageQueryDTO);
    @Update("update employee set status=#{status} where id=#{id}")
    void updateStatus(Integer status, Long id);

    void update(Employee employee);
}
