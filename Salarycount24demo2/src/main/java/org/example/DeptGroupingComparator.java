package org.example;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class DeptGroupingComparator extends WritableComparator {

    protected DeptGroupingComparator() {
        super(DeptSalaryKey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        DeptSalaryKey key1 = (DeptSalaryKey) a;
        DeptSalaryKey key2 = (DeptSalaryKey) b;
        return Integer.compare(key1.getDeptno(), key2.getDeptno());
    }
}
