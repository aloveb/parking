package com.pig.park.repository;

import com.pig.park.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
    @Autowired
    private UserRepository userRepository ;

   @RequestMapping(value = "/regist",method = RequestMethod.POST)//添加用户信息
    public boolean userRegist(@RequestParam("openId") String openId, @RequestParam("userName") String userName,
                             @RequestParam("cardId") String cardId, @RequestParam("plateNum") String plateNum){
        if(null != userRepository.findByOpenId(openId))//判断数据库内是否已经有该用户
            return false;
        User user = new User();
        user.setOpenId(openId);
        user.setUserName(userName);
        user.setCardId(cardId);
        user.setPlateNum(plateNum);
        user.setPurse(150);
        userRepository.save(user);
        return true;
    }

    @RequestMapping(value = "/edituser",method = RequestMethod.POST)//编辑用户信息
    public boolean editUserByopenId(@RequestParam("openId") String openId,@RequestParam("userName") String userName,@RequestParam("cardId") String cardId,@RequestParam("plateNum") String plateNum){
        userRepository.editUserByopenId(openId,userName,cardId,plateNum);
        return true;
    }

    @RequestMapping(value = "/purse",method = RequestMethod.GET)//获取用户账户内的猪猪币余额
    public int myPurse(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user.getPurse();
    }

}
