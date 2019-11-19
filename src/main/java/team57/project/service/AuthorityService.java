package team57.project.service;

import team57.project.model.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> findById(Long id);
    List<Authority> findByname(String name);
}
