package com.github.model;

import java.io.Serializable;
import java.util.Objects;


/**
 * 单元格模型
 * 以excel表格左上角第一个单元格为原点建立平面直角坐标系，向右为x轴正方向，向下为y轴正方向单元格x轴坐标
 *
 * @author 杨中肖
 * @date 2021/09/10
 */
public class CellModel implements Serializable {
    /**
     * x轴坐标（左上角第一个单元格坐标为(0,0)）
     */
    private Integer x;
    /**
     * y轴坐标（左上角第一个单元格坐标为(0,0)）
     */
    private Integer y;
    private String value;

    public CellModel(Integer x, Integer y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public CellModel() {
    }

    @Override
    public String toString() {
        return "CellModel{" +
                "x=" + x +
                ", y=" + y +
                ", value='" + value + '\'' +
                '}';
    }

    public Integer getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellModel cellModel = (CellModel) o;
        return Objects.equals(x, cellModel.x) && Objects.equals(y, cellModel.y) && Objects.equals(value, cellModel.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, value);
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
