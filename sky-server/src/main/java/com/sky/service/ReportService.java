package com.sky.service;

import com.sky.dto.DataOverViewQueryDTO;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportService {
    TurnoverReportVO turnover(LocalDate begin, LocalDate end);

    UserReportVO userOver(LocalDate begin,LocalDate end);

    OrderReportVO orderOver(LocalDate begin, LocalDate end);

    SalesTop10ReportVO salesTop10(LocalDate begin, LocalDate end);
}
