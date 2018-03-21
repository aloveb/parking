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

   @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public boolean userRegist(@RequestParam("openId") String openId, @RequestParam("userName") String userName,
                             @RequestParam("cardId") String cardId, @RequestParam("plateNum") String plateNum){
        User user = new User();
        user.setOpenId(openId);
        user.setUserName(userName);
        user.setCardId(cardId);
        user.setPlateNum(plateNum);
        user.setPurse(150);
        userRepository.save(user);
        return true;
    }

    @RequestMapping(value = "/edituser")
    public boolean editUserByopenId(@RequestParam("openId") String openId,@RequestParam("userName") String userName,@RequestParam("cardId") String cardId,@RequestParam("plateNum") String plateNum){
        userRepository.editUserByopenId(openId,userName,cardId,plateNum);
        return true;
    }

    @RequestMapping(value = "/purse")
    public int myPurse(@RequestParam("openId") String openId){
        User user = userRepository.findByOpenId(openId);
        return user.getPurse();
    }

}
