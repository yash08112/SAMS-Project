package com.sams.dao;

import com.sams.model.Subject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SubjectDAO implements GenericDAO<Subject, String> {
    private final Map<String, Subject> store = new LinkedHashMap<>();

    @Override
    public void save(Subject s) {
        if (s == null || s.getSubjectId() == null) throw new IllegalArgumentException("Subject/ID null.");
        if (store.containsKey(s.getSubjectId())) throw new IllegalStateException("ID exists: " + s.getSubjectId());
        store.put(s.getSubjectId(), s);
    }

    @Override
    public Optional<Subject> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Subject> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(String id) {
        return store.remove(id) != null;
    }

    @Override
    public boolean update(Subject s) {
        if (!store.containsKey(s.getSubjectId())) return false;
        store.put(s.getSubjectId(), s);
        return true;
    }

    public Optional<Subject> findByCode(String code) {
        return store.values().stream()
                .filter(s -> s.getSubjectCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public List<Subject> findByFaculty(String faculty) {
        return store.values().stream()
                .filter(s -> s.getFacultyName().equalsIgnoreCase(faculty))
                .collect(Collectors.toList());
    }

    public int getCount() {
        return store.size();
    }
}

