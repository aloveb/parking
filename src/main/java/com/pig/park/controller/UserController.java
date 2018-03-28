package com.pig.park.controller;

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
     * @return  如果用户注册成功则返回用户信息， null则用户注册失败
     */
   @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody User userRegister(@RequestBody User user){
        user.setPurse(150);
        return userRepository.save(user);
    }

    /**
     * 检查用户是否存在
     * @param openId 微信的OpenID
     * @return 如果用户存在则直接返回用户，否则为null
     */
    @RequestMapping(value = "/checkuser",method = RequestMethod.GET)//根据ID检查用户是否存在
    public @ResponseBody User checkUser(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user;
    }

    /**
     * 根据用户ID获取用户的钱币
     * @param id 用户ID
     * @return 用户如果存在则是钱币，不存在则返回-1
     */
    @RequestMapping(value = "/purse",method = RequestMethod.GET)//获取用户账户内的猪猪币余额
    public @ResponseBody int myPurse(@RequestParam("id")Long id){
        User user = userRepository.findByid(id);
        return user == null ? -1 : user.getPurse();
    }

    /**
     * 更新用户
     * @param user 需要更新用户的信息
     * @return 修改后返回修改后的user信息
     */
    @RequestMapping(value = "/edit",method = RequestMethod.PUT)//修改订单
    public @ResponseBody User editOrder(@RequestBody User user) {
        User newuser = userRepository.findByid(user.getId());
        newuser.setUserName(user.getUserName());
        newuser.setCardId(user.getCardId());
        newuser.setPlateNum(user.getPlateNum());
        return userRepository.save(newuser);
    }

    /** for test
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)//获取用户账户内的猪猪币余额
    public @ResponseBody void delete(@RequestParam("id")Long id){
        userRepository.deleteByid(id);
    }
}
