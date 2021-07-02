 package com.springboot.test.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.test.annotation.Secret;
import com.springboot.test.model.Page;
import com.springboot.test.model.po.User;
import com.springboot.test.model.vo.BaseVO;
import com.springboot.test.model.vo.ResultVO;
import com.springboot.test.model.vo.UserGroupVo;
import com.springboot.test.model.vo.UserVO;
import com.springboot.test.service.IUserService;

@Secret(BaseVO.class)                             //接口参数和返回要进行加解密
@RestController
@RequestMapping("user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private IUserService userService;
    
    //采用内部类的实例代码块方式初始化map
    Map<Integer, UserVO> userMap = new HashMap<Integer, UserVO>(){
        private static final long serialVersionUID = -5303894088236344897L;
        {
            put(1,new UserVO(1,"张三"));
            put(2,new UserVO(2,"李四"));
            put(3,new UserVO(3,"王五"));
        }
    };
 
    // 通过id查询用户
    @GetMapping("getUserName/{id}")
    public ResultVO getUserName(@PathVariable("id")  Integer id){
        return new ResultVO(0,"查询成功",userMap.get(id));
    }
 
    // 通过name查询用户id
    @GetMapping("getUserId")
    public ResultVO getUserId(@RequestParam  String name){
        Iterator<Map.Entry<Integer, UserVO>> iterator = userMap.entrySet().iterator();
        UserVO u = null;
        while (iterator.hasNext()){
            Map.Entry<Integer, UserVO> entry = iterator.next();
            if(entry.getValue().getName().equals(name)){
                u = entry.getValue();
                break;
            }
        }
        return new ResultVO(0,"查询成功",u);
    }
 
    // 新增用户
    @PostMapping("addUser")
    public ResultVO addUser(@RequestBody UserVO user){
        return new ResultVO(0,"新增成功",user);
    }
 
    // 更改用户
    @PostMapping("updateUser")
    public ResultVO updateUser(@RequestBody UserVO user) throws Throwable {
        if(user==null||user.getId()==null){
            throw new NullPointerException();
        }else{
            return new ResultVO(0,"修改成功",user);
        }
    }
    @GetMapping("/{uid}")
    public User findUserById(@PathVariable("uid") String uid) {
        User user = userService.findUserById(uid);
        return user;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.insertUser(user);

    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/delete/{uid}")
    public void deleteUser(@PathVariable("uid") String uid) {
        userService.deleteUser(uid);

    }

    @GetMapping("/findByParams")
    public List<User> findAllUser(@RequestParam(defaultValue = "") String name,
                                  @RequestParam(defaultValue = "") String gid) {
        List<User> users = userService.findAllUser(name,gid);
        return users;
    }

    @GetMapping("/find/{uid}")
    public UserGroupVo findUserGroupVo(@PathVariable("uid") String uid) {
        UserGroupVo userGroupVo = userService.findUserGroupVo(uid);
        logger.info("findUserGroupVo =>" + userGroupVo.toString());
        return userGroupVo;
    }

    @GetMapping("/findByCondition")
    public List<User> findUserByCondition(User user) {
        return userService.findUserByCondition(user);
    }
    
    @GetMapping("/findByPage")
    public List<User> findUserByPage(Page page) {
        return userService.findAllUser(page);
    }
}
