package com.sams.service;

import com.sams.dao.StudentDAO;
import com.sams.exception.SAMSException;
import com.sams.model.Student;
import com.sams.util.IDGenerator;
import com.sams.util.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentDAO dao;

    public StudentService(StudentDAO dao) {
        this.dao = dao;
    }

    public Student registerStudent(String name, String email, String course, int semester, String phone) {
        Student s = new Student(IDGenerator.generateStudentId(),
                name.trim(), email.trim().toLowerCase(), course.trim(), semester, phone.trim());
        Validator.validateStudent(s);
        try {
            dao.save(s);
        } catch (IllegalStateException e) {
            throw new SAMSException.DuplicateStudentException(s.getStudentId());
        }
        return s;
    }

    public Student getStudentById(String id) {
        return dao.findById(id).orElseThrow(() -> new SAMSException.StudentNotFoundException(id));
    }

    public List<Student> getAllStudents() {
        return dao.findAll();
    }

    public List<Student> getStudentsByCourse(String c) {
        return dao.findByCourse(c);
    }

    public List<Student> getStudentsBySemester(int sem) {
        return dao.findBySemester(sem);
    }

    public List<Student> searchByName(String part) {
        return dao.searchByName(part);
    }

    public boolean studentExists(String id) {
        return dao.findById(id).isPresent();
    }

    public int getTotalStudentCount() {
        return dao.getCount();
    }

    public Student updateStudent(String id, String name, String email, String course, int sem, String phone) {
        Student s = getStudentById(id);
        s.setName(name.trim());
        s.setEmail(email.trim());
        s.setCourse(course.trim());
        s.setSemester(sem);
        s.setPhoneNumber(phone.trim());
        Validator.validateStudent(s);
        dao.update(s);
        return s;
    }

    public boolean removeStudent(String id) {
        getStudentById(id);
        return dao.deleteById(id);
    }
}

