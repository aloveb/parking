package com.pig.park.repository;

import com.pig.park.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByOpenId(String openId);
    @Modifying
    @Query(value = "update User u set u.userName=:userName," +
            " u.cardId=:cardId, u.plateNum=:plateNum where u.openId=:openId")
    void editUserByopenId(@Param("openId") String openId, @Param("userName") String userName,
                          @Param("cardId") String cardId, @Param("plateNum") String plateNum);

    @Query(value = "select purse from User where openId=:openId")
    int findpurseByopenId(@Param("openId") String openId);
}