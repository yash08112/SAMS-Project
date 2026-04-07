package com.sams.service;

import com.sams.dao.SubjectDAO;
import com.sams.exception.SAMSException;
import com.sams.model.Subject;
import com.sams.util.IDGenerator;
import com.sams.util.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectDAO dao;

    public SubjectService(SubjectDAO dao) {
        this.dao = dao;
    }

    public Subject addSubject(String name, String code, String faculty, int totalClasses) {
        Subject s = new Subject(IDGenerator.generateSubjectId(),
                name.trim(), code.trim().toUpperCase(), faculty.trim(), totalClasses);
        Validator.validateSubject(s);
        dao.save(s);
        return s;
    }

    public Subject getSubjectById(String id) {
        return dao.findById(id).orElseThrow(() -> new SAMSException.SubjectNotFoundException(id));
    }

    public List<Subject> getAllSubjects() {
        return dao.findAll();
    }

    public boolean subjectExists(String id) {
        return dao.findById(id).isPresent();
    }

    public int getTotalSubjectCount() {
        return dao.getCount();
    }

    public boolean removeSubject(String id) {
        getSubjectById(id);
        return dao.deleteById(id);
    }

    public void incrementTotalClasses(String id) {
        Subject s = getSubjectById(id);
        s.setTotalClasses(s.getTotalClasses() + 1);
        dao.update(s);
    }
}

