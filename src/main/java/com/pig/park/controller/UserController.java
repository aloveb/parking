package com.pig.park.controller;

import com.pig.park.entity.Order;
import com.pig.park.entity.User;
import com.pig.park.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository ;

   @RequestMapping(value = "/register",method = RequestMethod.POST)//添加用户信息
    public @ResponseBody boolean userRegister(@RequestBody User user){
        if(null != userRepository.findByOpenId(user.getOpenId()))//判断数据库内是否已经有该用户
            return false;
        user.setPurse(150);
        userRepository.save(user);
        return true;
    }

    @RequestMapping(value = "/checkUser",method = RequestMethod.GET)//根据ID检查用户是否存
    public @ResponseBody User checkUser(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user;
    }

    @RequestMapping(value = "/purse",method = RequestMethod.GET)//获取用户账户内的猪猪币余额
    public @ResponseBody int myPurse(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user == null ? 0 : user.getPurse();
    }
}
