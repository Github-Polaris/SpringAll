package com.springboot.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.springboot.bean.Student;

public interface StudentService {
	Student update(Student student);
	void deleteStudentBySno(String sno);
	Student queryStudentBySno(String sno);
}
