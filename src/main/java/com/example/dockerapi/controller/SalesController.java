package com.example.dockerapi.controller;

import com.example.dockerapi.model.Order;
import com.example.dockerapi.repository.OrderRepository;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.CsvWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@RestController
public class SalesController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/sales/export")
    public void exportSalesCsv(
            @RequestParam(value = "year", required = false) String yearStr,
            @RequestParam(value = "month", required = false) String monthStr,
            @RequestParam(value = "day", required = false) String dayStr,
            HttpServletResponse response) throws Exception {

        LocalDate date;
        try {
            if (yearStr == null || yearStr.isBlank() ||
                    monthStr == null || monthStr.isBlank() ||
                    dayStr == null || dayStr.isBlank()) {

                // デフォルト: 日本時間の今日
                date = LocalDate.now(ZoneId.of("Asia/Tokyo"));
            } else {
                date = LocalDate.of(
                        Integer.parseInt(yearStr),
                        Integer.parseInt(monthStr),
                        Integer.parseInt(dayStr));
            }
        } catch (Exception e) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('日付を正しく入力してください。');window.history.back();</script>");
            return;
        }

        List<Order> allOrders = orderRepository.findAll();
        List<Order> filtered = allOrders.stream()
                .filter(o -> o.getOrderDate().toLocalDate().equals(date))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter()
                    .write("<script>alert('該当するデータがありませんでした。日付を正しく入力してください。');window.history.back();</script>");
            return;
        }

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"sales_" + date + ".csv\"");

        CsvConfig config = new CsvConfig();
        config.setQuote('"');
        config.setSeparator(',');
        config.setEscape('\\');
        config.setQuoteDisabled(false);
        config.setLineSeparator("\r\n");

        CsvWriter writer = new CsvWriter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8),
                config);

        // ヘッダー
        writer.writeValues(Arrays.asList("orderId", "orderDate", "orderStatus", "productId", "productName",
                "totalPrice", "userId"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        // データ出力
        for (Order o : filtered) {
            ZonedDateTime jstDateTime = o.getOrderDate()
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(ZoneOffset.ofHours(9));
            String formattedDate = jstDateTime.format(formatter);

            writer.writeValues(Arrays.asList(
                    String.valueOf(o.getOrderId()),
                    formattedDate, // ★ JST補正＋整形後の日付
                    String.valueOf(o.getOrderStatus()),
                    String.valueOf(o.getProductId()),
                    o.getProductName(),
                    String.valueOf(o.getTotalPrice()),
                    String.valueOf(o.getUserId())));
        }

        writer.close();
    }
}
