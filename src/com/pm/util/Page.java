package com.pm.util;

import java.util.List;

/**
 * 处理分页列表数据
 * @author smallis
 */
public class Page<E> {
    /**
     * rows: table显示行数
     * list: 数据列表
     */
    private E e;
    private int rows;

    public Page(int rows) {
        this.rows = rows;
    }

    /**
     * 将传入的list根据index切分为所需要的大小
     * @param index 位置索引
     * @param list 数据列表
     * @return 返回切分好的list1
     */
    public List<E> cutList(int index, List<E> list) {

        int listLength = list.size();
        if (index < 1) {
            index = 1;
        }
        int start = (index - 1) * rows;
        int end = start + rows;

        if (end >= listLength) {
            end = listLength;
        }
        List<E> list1 = list.subList(start, end);
        return list1;
    }
}
