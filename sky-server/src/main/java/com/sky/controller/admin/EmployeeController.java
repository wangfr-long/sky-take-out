package com.sky.controller.admin;

import com.alibaba.fastjson.JSON;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录接口")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, (long) employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增员工")
    public Result<String> addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result selectLimit(EmployeePageQueryDTO pageQueryDTO) {
        PageResult page = employeeService.selectLimit(pageQueryDTO);
        return Result.success(page);
    }

    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status, Long id) {
        employeeService.setStatus(status, id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<Employee> selectById(@PathVariable Long id){
        Employee employee=employeeService.selectById(id);
        employee.setPassword("******");
        log.info("员工信息{}",employee.toString());
        return Result.success(employee);
    }
    @PutMapping
    public Result updateEmp(@RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmp(employeeDTO);
        return Result.success();
    }
    @PutMapping("/editPassword")
    public Result updatePassword(@RequestBody PasswordEditDTO passwordEditDTO){
        employeeService.updatePassword(passwordEditDTO);
        return Result.success();
    }
}
