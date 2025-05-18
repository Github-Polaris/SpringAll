package dev.polaris;

import com.springboot.RedisCacheApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springboot.bean.Student;
import com.springboot.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisCacheApplication.class)
public class RedisCacheApplicationTest {

	@Autowired
	private StudentService studentService;

	@Test
    public void test1() throws Exception {
        Student student1 = this.studentService.queryStudentBySno("001");
        System.out.println("学号" + student1.getSno() + "的学生姓名为：" + student1.getName());
        
        Student student2 = this.studentService.queryStudentBySno("001");
        System.out.println("学号" + student2.getSno() + "的学生姓名为：" + student2.getName());
    }
	
	@Test
	public void test2() throws Exception {
		Student student1 = this.studentService.queryStudentBySno("001");
		System.out.println("学号" + student1.getSno() + "的学生姓名为：" + student1.getName());

		student1.setName("康康");
		this.studentService.update(student1);

		/*
		* 这里仍然会从数据库中查询，其原因是缓存未命中
		* update方法使用的是@CachePut(key = "#p0.sno")作为缓存key
		* */
		Student student2 = this.studentService.queryStudentBySno("001");
		System.out.println("学号" + student2.getSno() + "的学生姓名为：" + student2.getName());
	}
}
