package com.sky.vo;

import com.sky.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO implements Serializable {
    private Long total;
    private List<Employee> records;
}
