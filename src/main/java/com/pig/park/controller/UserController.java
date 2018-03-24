package com.pig.park.controller;

import com.pig.park.entity.Order;
import com.pig.park.entity.User;
import com.pig.park.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository ;

    /**
     * 注册用户，条件是判断用户是否存在
     * @param user 用户注册的信息
     * @return true：用户注册成功， false：用户注册失败
     */
   @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody boolean userRegister(@RequestBody User user){
        if(null != userRepository.findByOpenId(user.getOpenId()))//判断数据库内是否已经有该用户
            return false;
        user.setPurse(150);
        userRepository.save(user);
        return true;
    }

    /**
     * 检查用户是否存在
     * @param openId 微信的OpenID
     * @return 如果用户存在则直接返回用户，否则为null
     */
    @RequestMapping(value = "/checkUser",method = RequestMethod.GET)//根据ID检查用户是否存
    public @ResponseBody User checkUser(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user;
    }

    /**
     * 根据微信ID获取用户的钱币
     * @param openId 微信ID
     * @return 用户如果存在则是钱币，不存在则返回-1
     */
    @RequestMapping(value = "/purse",method = RequestMethod.GET)//获取用户账户内的猪猪币余额
    public @ResponseBody int myPurse(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user == null ? -1 : user.getPurse();
    }
}
