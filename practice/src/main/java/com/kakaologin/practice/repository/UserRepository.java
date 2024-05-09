package com.kakaologin.practice.repository;

import com.kakaologin.practice.domain.User;

import java.util.List;

public interface UserRepository {
    User findOne(Long userid);
    User findOneByKakaoId(Long kakaoId);

    List<User> findAll();
    User join(User user);
    void delete(Long userId);
    void deleteByKakaoId(Long kakaoId);
}
