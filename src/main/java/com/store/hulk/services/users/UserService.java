/**
 * @author Aldair mosquera murillo
 */
package com.store.hulk.services.users;

import com.store.hulk.models.customers.Customer;
import com.store.hulk.models.users.UserHulk;
import com.store.hulk.repositories.users.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service @Slf4j
public class UserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserHulk save(UserHulk user){
        log.info("Save user: {}",user.getName());
        try {
            if (!user.getPassword().startsWith("$")) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            return repository.save(user);
        }catch (DataAccessException e){
            log.error("Error save User:{}",e.getMessage());
        }
    return  new UserHulk();
    }

    public UserHulk findByUserName(String userName){
        return repository.findByUserName(userName);
    }

    public Optional<UserHulk> findById(Long id){
        log.info("Find user by id: {}",id);
        return  repository.findById(id);
    }
    public boolean exist(Long id){
            return  repository.existsById(id);
    }
    public Iterable<UserHulk> getAll(){
        log.info("Get all users");
        return repository.findAll();
    }

    public Iterable<UserHulk> typeHeadSearch(String query) {
        log.info("type head search by query: {}",query);
        if (query.equals("")) {
            return new ArrayList<>();
        }
        query = "%" + query + "%";
        return repository.typeHeadSearch(query);
    }

    public Page<UserHulk> typeHeadSearchPage(String query, Pageable pageable) {
        log.info("type head search by query: {}",query);
        query = "%" + query + "%";
        return repository.typeHeadSearchPage(query, pageable);
    }
}
