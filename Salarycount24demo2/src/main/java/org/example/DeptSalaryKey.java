package org.example;
import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DeptSalaryKey implements WritableComparable<DeptSalaryKey> {
    private int deptno; // 部门号
    private int sal;    // 薪资

    public DeptSalaryKey() {}

    public DeptSalaryKey(int deptno, int sal) {
        this.deptno = deptno;
        this.sal = sal;
    }

    public int getDeptno() {
        return deptno;
    }

    public int getSal() {
        return sal;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public void setSal(int sal) {
        this.sal = sal;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(deptno);
        out.writeInt(sal);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        deptno = in.readInt();
        sal = in.readInt();
    }

    @Override
    public int compareTo(DeptSalaryKey o) {
        // 按部门号升序，部门内部按薪资降序
        int result = Integer.compare(this.deptno, o.deptno);
        if (result == 0) {
            result = Integer.compare(o.sal, this.sal); // 薪资降序
        }
        return result;
    }

    @Override
    public int hashCode() {
        return deptno * 163 + sal;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeptSalaryKey that = (DeptSalaryKey) obj;
        return deptno == that.deptno && sal == that.sal;
    }
}
