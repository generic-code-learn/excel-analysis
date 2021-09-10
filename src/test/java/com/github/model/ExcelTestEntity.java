package com.github.model;


import com.github.annotation.ExcelCell;

import java.io.Serializable;
import java.util.Objects;

/**
 * excel的实体
 *
 * @author 杨中肖
 * @date 2021/09/06
 */
public class ExcelTestEntity implements Serializable {
    @ExcelCell(headerName = {"ID", "ID"})
    private String id;
    @ExcelCell(headerName = {"计量点ID", "计量点ID"})
    private String mid;
    @ExcelCell(headerName = {"电量", "正向有功计算电量(kWh)"})
    private Double p;

    public ExcelTestEntity() {
    }

    public ExcelTestEntity(String id, String mid, Double p) {
        this.id = id;
        this.mid = mid;
        this.p = p;
    }

    @Override
    public String toString() {
        return "ExcelTestEntity{" +
                "id='" + id + '\'' +
                ", mid='" + mid + '\'' +
                ", p=" + p +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelTestEntity that = (ExcelTestEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(mid, that.mid) && Objects.equals(p, that.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mid, p);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Double getP() {
        return p;
    }

    public void setP(Double p) {
        this.p = p;
    }
}
