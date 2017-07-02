package com.vn.nev.SpringMvcDemo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vn.nev.SpringMvcDemo.model.Span;
import com.vn.nev.SpringMvcDemo.model.Student;
import com.vn.nev.SpringMvcDemo.service.StudentService;
import com.vn.nev.SpringMvcDemo.service.impl.StudentServiceImpl;

@Controller
public class HomeController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentServiceImpl studentService2;
	private int editStudentID;
	private int nameAsc = -1;
	private int gpaAsc = -1;

	/*
	 * show student list controller
	 */
	@RequestMapping({ "/index" })
	public String index(Model model) {
		model.addAttribute("students", studentService.getAll());
		model.addAttribute("message", studentService.getAll().size() + " " + studentService2.getAll().size());

		return "home/index";
	}

	/*
	 * add student controller
	 */
	@GetMapping("/add")
	public String studentForm(Model model) {
		model.addAttribute("student", new Student());

		return "home/add";
	}

	/*
	 * handle add student form
	 */
	@PostMapping("/add")
	public String studentSubmit(@ModelAttribute Student student) {
		student.setId(studentService.genID());
		studentService.add(student);

		return "redirect:/index";
	}

	/*
	 * remove student controller
	 */
	@RequestMapping({ "/remove" })
	public String delete(Model model, int id) {
		studentService.removeById(id);

		return "redirect:/index";
	}

	/*
	 * edit student controller
	 */
	@GetMapping("/edit")
	public String edit(Model model, int id) {
		model.addAttribute("student", studentService.getById(id));
		editStudentID = id;
		return "home/edit";
	}

	/*
	 * handle edit student form back to index home
	 */
	@PostMapping("/edit")
	public String editStudentSubmit(@ModelAttribute Student student, Model model) {
		student.setId(editStudentID);
		studentService.editById(student);

		return "redirect:/index";
	}

	/*
	 * sort by name controller
	 */
	@RequestMapping({ "/sortByName" })
	public String sortByName(Model model) {
		model.addAttribute("message", "student list sort by name");
		model.addAttribute("students", studentService.getAllOrderByName(nameAsc));
		nameAsc *= -1;
		return "home/index";
	}

	/*
	 * sort by gpa controller
	 */
	@RequestMapping({ "/sortByGpa" })
	public String sortByGpa(Model model) {
		model.addAttribute("message", "student list sort by gpa");
		model.addAttribute("students", studentService.getAllOrderByGpa(gpaAsc));
		gpaAsc *= -1;
		return "home/index";
	}

	@PostMapping("/ahihi/dongok")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody Span span, Errors errors) {
        System.out.println("student: "+span.getId());
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(span);
        }
        Student ratingStudent=studentService.getById(span.getId());
        ratingStudent.setRating(1+ratingStudent.getRating());
        studentService.editById(ratingStudent);
        return ResponseEntity.ok(span);
    }

}
