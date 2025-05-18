package com.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.springboot.bean.Student;
import com.springboot.mapper.StudentMapper;
import com.springboot.service.StudentService;

/**
 * 当前接口中所有使用 @Cacheable、@CachePut、@CacheEvict 注解的方法，默认都会操作名为 "student" 的缓存区域。
 * 可以把它理解为：这些方法操作的是 Redis 中的一个逻辑缓存区域，名字叫 student。
 * */
@Repository("studentService")
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentMapper;

	/**
	 * p0 = parameter 0，即方法的第一个参数，这里p0是一个Student对象，那么p0.sno就是这个对象里的sno属性值
	 * 这表明会使用p0.sno的属性值，作为缓存的key
	 * Spring 官方不推荐直接在接口方法上使用 @Cache* 注解
	 * 直接在接口方法上加 @Cacheable 等注解，当目标类实现了该接口但没有使用 CGLIB 代理时，可能会导致缓存行为失效
	 * */
	@Override
//	@CachePut(key = "#p0.sno")
	@CachePut(keyGenerator = "keyGenerator")
	public Student update(Student student) {
		this.studentMapper.update(student);
		return this.studentMapper.queryStudentBySno(student.getSno());
	}

	/**
	 * 表明会使用p0的属性值，作为key来清除缓存
	 * allEntries = true 表示：清除整个缓存中的所有条目（不仅仅是与当前 key 匹配的条目）。
	 * */
	@Override
	//	@CacheEvict(key = "#p0", allEntries = true)
	@CacheEvict(key = "#p0")
	public void deleteStudentBySno(String sno) {
		this.studentMapper.deleteStudentBySno(sno);
	}

	@Override
//	@Cacheable(key = "#p0")
	@Cacheable(keyGenerator = "keyGenerator")
	public Student queryStudentBySno(String sno) {
		return this.studentMapper.queryStudentBySno(sno);
	}

}
