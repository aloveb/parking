package com.pig.park.repository;

import com.pig.park.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
    @Autowired
    private UserRepository userRepository ;

   @RequestMapping(value = "/regist")
    public String userRegist(@RequestParam("openId") String openId, @RequestParam("userName") String userName,
                             @RequestParam("cardId") String cardId, @RequestParam("plateNum") String plateNum){
        User user = new User();
        user.setOpenId(openId);
        user.setUserName(userName);
        user.setCardID(cardId);
        user.setPlateNum(plateNum);
        user.setPurse(150);
        userRepository.save(user);
        return "success!";
    }

    @RequestMapping(value = "/edituser")
    public String editUserByopenId(@RequestParam("openId") String openId,@RequestParam("userName") String userName,@RequestParam("cardId") String cardId,@RequestParam("plateNum") String plateNum){
        userRepository.editUserByopenId(openId,userName,cardId,plateNum);
        return "success!";
    }

    @RequestMapping(value = "/purse")
    public int findpurseByopenId(@RequestParam("openId") String openId){
        return userRepository.findpurseByopenId(openId);
    }

}
