package com.github.analysis;

import com.github.model.ExcelTestEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ExcelAnalysisTest {
    private long startTime;

    @Before
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void end() {
        long endTime = System.currentTimeMillis();
        System.out.printf("解析耗时 %sms%n", endTime - startTime);
    }

    @Test
    public void analysis() throws IOException {
        List<ExcelTestEntity> list = ExcelAnalysis.analysis("E:\\test.xls", ExcelTestEntity.class, 2);
        System.out.printf("解析了%s条数据%n", list.size());
        list.forEach(System.out::println);
    }
}