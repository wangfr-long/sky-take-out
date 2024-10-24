package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrdeiDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrdeiDetailMapper ordeiDetailMapper;

    @Override
    public TurnoverReportVO turnover(LocalDate begin, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        while (!begin.equals(end)) {
            dates.add(begin);
            begin = begin.plusDays(1);
        }
        dates.add(end);
        List<Long> counts = new ArrayList<>();
        for (LocalDate date : dates) {
            Long count = orderMapper.sumTurnover(date);
            if (count == null) {
                count = 0L;
            }
            counts.add(count);
        }
        String dateList = StringUtils.join(dates, ",");
        String turnoverList = StringUtils.join(counts, ",");
        TurnoverReportVO turnoverReportVO = TurnoverReportVO.builder()
                .dateList(dateList)
                .turnoverList(turnoverList)
                .build();
        return turnoverReportVO;

    }

    @Override
    public UserReportVO userOver(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        while (!begin.equals(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }
        dateList.add(end);
        List<Long> totalUserList = new ArrayList<>();
        List<Long> newUserList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            Long l = userMapper.select(localDate);
            Long newNumber = userMapper.selectByCreatTime(localDate);
            totalUserList.add(l);
            newUserList.add(newNumber);
        }
        String totalUserLists = StringUtils.join(totalUserList, ",");
        String newUserLists = StringUtils.join(newUserList, ",");
        String dateLists = StringUtils.join(dateList, ",");
        UserReportVO userReportVO = UserReportVO.builder()
                .totalUserList(totalUserLists)
                .newUserList(newUserLists)
                .dateList(dateLists)
                .build();
        return userReportVO;
    }

    @Override
    public OrderReportVO orderOver(LocalDate begin, LocalDate end) {
        List<LocalDate> localDates = new ArrayList<>();
        while (!begin.equals(end)) {
            localDates.add(begin);
            begin = begin.plusDays(1);
        }
        localDates.add(end);
        List<Long> orderCountList = new ArrayList<>();
        List<Long> validOrderCountList = new ArrayList<>();
        for (LocalDate localDate : localDates) {
            Long orderCountDate = orderMapper.count(localDate, null);
            orderCountList.add(orderCountDate);
            Long validCountDate = orderMapper.count(localDate, Orders.COMPLETED);
            validOrderCountList.add(validCountDate);
        }
        Long orderCount = orderMapper.count(null, null);
        Long validOrderCount = orderMapper.count(null, Orders.COMPLETED);
        Double orderCompletionRate = 0.0;
        if (orderCount != 0) {
            orderCompletionRate = (double) validOrderCount / orderCount;
        }
        OrderReportVO orderReportVO = OrderReportVO.builder()
                .orderCompletionRate(orderCompletionRate)
                .totalOrderCount(Math.toIntExact(orderCount))
                .dateList(StringUtils.join(localDates, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .validOrderCount(Math.toIntExact(validOrderCount))
                .build();
        return orderReportVO;
    }

    @Override
    public SalesTop10ReportVO salesTop10(LocalDate begin, LocalDate end) {
        List<String> names = ordeiDetailMapper.reportName(begin, end);
        List<Long> numbers = ordeiDetailMapper.reportNumber(begin, end);
        String name = StringUtils.join(names, ",");
        String number = StringUtils.join(numbers, ",");
        SalesTop10ReportVO reportVO = SalesTop10ReportVO.builder()
                .nameList(name)
                .numberList(number)
                .build();
        return reportVO;
    }
}
