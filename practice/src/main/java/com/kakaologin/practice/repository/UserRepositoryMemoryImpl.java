package com.kakaologin.practice.repository;

import com.kakaologin.practice.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class UserRepositoryMemoryImpl implements UserRepository{

    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public User findOne(Long userid) {
        User findUser = null;
        if (userMap.containsKey(userid)){
            findUser = userMap.get(userid);
        }
        return findUser;
    }

    @Override
    public User findOneByKakaoId(Long kakaoId) {
        AtomicReference<Long> findId = new AtomicReference<>(-1L);
        List<User> userList = findAll();
        User findUser = null;
        userList.iterator().forEachRemaining((User u)->{
            if (u.getKakaoId().equals(kakaoId)){
                findId.set(u.getId());
            }
        });
        if (findId.get() != -1L){
            findUser = userMap.get(findId.get());
        }
        return findUser;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        userMap.values().iterator().forEachRemaining((User user)->userList.add(user));
        return userList;
    }

    @Override
    public User join(User user) {
        if (user.getKakaoId() != null){
            List<User> userList = findAll();
            userList.iterator().forEachRemaining((User u)->{
                if(u.getKakaoId().equals(user.getKakaoId())){
                    user.setId(-1L);
                }
            });
        }
        if (user.getId().equals(-1L)){
            return null;
        }
        user.setId(sequence);
        userMap.put(sequence++, user);
        return user;
    }

    @Override
    public void delete(Long userId) {
        User deleteUser = findOne(userId);
        if (deleteUser != null){
            userMap.remove(deleteUser.getId());
        }
    }

    @Override
    public void deleteByKakaoId(Long kakaoId) {
        User deleteUser = findOneByKakaoId(kakaoId);
        if (deleteUser != null){
            userMap.remove(deleteUser.getId());
        }
    }
}
