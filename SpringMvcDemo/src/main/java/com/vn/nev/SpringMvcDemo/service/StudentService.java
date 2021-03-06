package com.vn.nev.SpringMvcDemo.service;

import java.util.List;

import com.vn.nev.SpringMvcDemo.model.Student;

public interface StudentService {
	List<Student> getAll();
	
	List<Student> get(int fromIndex, int toIndex);

	List<Student> getAllOrderByName(int Asc);

	List<Student> getAllOrderByGpa(int Asc);

	Student getById(int id);

	int removeById(int id);

	void editById(Student student);

	Student add(Student newStudent);
	
	int genID();
}
