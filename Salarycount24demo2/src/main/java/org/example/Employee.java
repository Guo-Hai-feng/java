package org.example;
import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Employee implements WritableComparable<Employee> {
	private int empno;        // 员工号
	private String ename;     // 姓名
	private String job;       // 职位
	private int mgr;          // 经理编号
	private String hiredate;  // 入职日期
	private int sal;          // 薪水
	private int comm;         // 奖金
	private int deptno;       // 部门号

	// 无参构造函数
	public Employee() {}

	// 各个字段的getter和setter
	public int getEmpno() { return empno; }
	public void setEmpno(int empno) { this.empno = empno; }

	public String getEname() { return ename; }
	public void setEname(String ename) { this.ename = ename; }

	public String getJob() { return job; }
	public void setJob(String job) { this.job = job; }

	public int getMgr() { return mgr; }
	public void setMgr(int mgr) { this.mgr = mgr; }

	public String getHiredate() { return hiredate; }
	public void setHiredate(String hiredate) { this.hiredate = hiredate; }

	public int getSal() { return sal; }
	public void setSal(int sal) { this.sal = sal; }

	public int getComm() { return comm; }
	public void setComm(int comm) { this.comm = comm; }

	public int getDeptno() { return deptno; }
	public void setDeptno(int deptno) { this.deptno = deptno; }

	// 序列化方法
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(empno);
		out.writeUTF(ename);
		out.writeUTF(job);
		out.writeInt(mgr);
		out.writeUTF(hiredate);
		out.writeInt(sal);
		out.writeInt(comm);
		out.writeInt(deptno);
	}

	// 反序列化方法
	@Override
	public void readFields(DataInput in) throws IOException {
		empno = in.readInt();
		ename = in.readUTF();
		job = in.readUTF();
		mgr = in.readInt();
		hiredate = in.readUTF();
		sal = in.readInt();
		comm = in.readInt();
		deptno = in.readInt();
	}

	// compareTo 方法实现降序排序
	@Override
	public int compareTo(Employee o) {
		return Integer.compare(o.getSal(), this.sal); // 按薪水降序排序
	}

	// toString 方法用于调试和输出
	@Override
	public String toString() {
		return empno + "\t" + ename + "\t" + job + "\t" + mgr + "\t" + hiredate +
				"\t" + sal + "\t" + comm + "\t" + deptno;
	}
}
