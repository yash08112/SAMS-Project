package com.sams.dao;

import com.sams.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StudentDAO implements GenericDAO<Student, String> {
    private final Map<String, Student> store = new LinkedHashMap<>();

    @Override
    public void save(Student s) {
        if (s == null || s.getStudentId() == null) throw new IllegalArgumentException("Student/ID null.");
        if (store.containsKey(s.getStudentId())) throw new IllegalStateException("ID exists: " + s.getStudentId());
        store.put(s.getStudentId(), s);
    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(String id) {
        return store.remove(id) != null;
    }

    @Override
    public boolean update(Student s) {
        if (!store.containsKey(s.getStudentId())) return false;
        store.put(s.getStudentId(), s);
        return true;
    }

    public List<Student> findByCourse(String course) {
        return store.values().stream()
                .filter(s -> s.getCourse().equalsIgnoreCase(course))
                .collect(Collectors.toList());
    }

    public List<Student> findBySemester(int sem) {
        return store.values().stream()
                .filter(s -> s.getSemester() == sem)
                .collect(Collectors.toList());
    }

    public List<Student> searchByName(String part) {
        String p = part == null ? "" : part;
        return store.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(p.toLowerCase()))
                .collect(Collectors.toList());
    }

    public int getCount() {
        return store.size();
    }
}

