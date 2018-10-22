package com.yibi.web.controller;


import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.PictureUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.ChatGroup;
import com.yibi.core.entity.ChatGroupUser;
import com.yibi.core.entity.User;
import com.yibi.core.service.ChatGroupService;
import com.yibi.core.service.ChatGroupUserService;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.rongcloud.request.RongCloudGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chat")
public class ChatController {
    @Autowired
    private ChatGroupService chatGroupService;
    @Autowired
    private ChatGroupUserService chatGroupUserService;
    @Autowired
    private RongCloudGroupRequest groupRequest;
    @Autowired
    private UserService userService;
    @RequestMapping("talkGroupListPage")
    public String talkGroupListPage(){
        return "chat/TalkGroupListPage";
    }
    @RequestMapping("talkGroupList")
    @ResponseBody
    public Object talkGroupList(){
        return this.chatGroupService.selectAll(null);
    }

    @RequestMapping("loadChatForm")
    @ResponseBody
    public Object loadChatForm(Integer id){
        return this.chatGroupService.selectByPrimaryKey(id);
    }

    @RequestMapping("saveOrUpdateChatPage")
    public String saveOrUpdateChatPage(Integer id,HttpServletRequest request){
        if (id != null){
            request.setAttribute("id",id);
        }
        return "chat/saveOrUpdateChatPage";
    }

    @RequestMapping("saveOrUpdateChat")
    @ResponseBody
    public Object saveOrUpdateChat(ChatGroup chatGroup, @RequestParam("file")MultipartFile file,String phone) throws Exception {
        Json json = new Json();
        json.setSuccess(true);
        if (chatGroup.getId() != null){//执行更新
            ChatGroup chatGroups = this.chatGroupService.selectByPrimaryKey(chatGroup.getId());
            if (!chatGroup.getGroupid().equals(chatGroups.getGroupid())){
                json.setMsg("不让改群组Id还改！");
                return json;
            }
            if (!file.isEmpty()){//更新了图片
                String fileName = file.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!"png".equalsIgnoreCase(fileSuffix)){
                    json.setSuccess(false);
                    json.setMsg("图片格式异常！");
                    return json;
                }
                //上传图片
                String imageName = "imageName"+new Date().getTime()+".jpg";
                String fileDir = "pictureTr";
                String imgPath = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
                chatGroup.setImgurl(imgPath);
            }
            this.chatGroupService.updateByPrimaryKeySelective(chatGroup);
            json.setMsg("更新群组成功");
            return json;
        }else {
            //验证输入的群主手机号
            String regx = "^1\\d{10}$";
            if(!phone.matches(regx)){
                json.setMsg("手机号格式不正确");
                return json;
            }
            //验证群主信息
            User user = userService.selectByPhone(phone);
            if(user == null){
                json.setMsg("要添加的群主账号不存在");
                return json;
            }
            Map<Object,Object> map = new HashMap<>();
            map.put("groupid",chatGroup.getGroupid());
            List<ChatGroup> list =  this.chatGroupService.selectAll(map);
            if (list.size() == 1){
                json.setMsg("要添加的群组Id已经存在了");
                return json;
            }
            if (!file.isEmpty()){
                String fileName = file.getOriginalFilename();
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
                if(!"png".equalsIgnoreCase(fileSuffix)){
                    json.setSuccess(false);
                    json.setMsg("图片格式异常！");
                    return json;
                }
                //上传图片
                String imageName = "imageName"+new Date().getTime()+".jpg";
                String fileDir = "pictureTr";
                String imgPath = PictureUtils.uploadImg(fileDir, imageName,file.getInputStream());
                chatGroup.setImgurl(imgPath);
                if (StrUtils.isBlank(imgPath)){
                    json.setMsg("上传图片到服务器失败");
                    return json;
                }
                //融云新建群
                 boolean result = groupRequest.create(chatGroup.getGroupid(),chatGroup.getName(),phone);
                if (!result){
                    json.setMsg("融云端创建群组失败");
                    return json;
                }
                //融云，把用户加入新增的群聊
                result = groupRequest.join(chatGroup.getGroupid(),chatGroup.getName(),phone);
                if (!result){
                    json.setMsg("融云群主加入新创建的群组失败");
                    return json;
                }
                //后台建群
                this.chatGroupService.insertSelective(chatGroup);
                //本地后台加入群主用户
                ChatGroupUser chatGroupUser = new ChatGroupUser();
                chatGroupUser.setRole(0);//管理员
                chatGroupUser.setState(1);
                chatGroupUser.setIsmuted(false);
                chatGroupUser.setTalkgroupid(chatGroup.getId());
                chatGroupUser.setUserid(user.getId());
                this.chatGroupUserService.insertSelective(chatGroupUser);

                json.setMsg("新增群组成功");
                return json;
            }else {
                json.setMsg("必须上传图片！");
                return json;
            }
        }
    }

    @RequestMapping("deleteChatGroup")
    @ResponseBody
    public Object deleteChatGroup(Integer id) throws Exception {//通过id解散群组
        Json json = new Json();
        json.setSuccess(true);
        boolean result = groupRequest.dismiss("18530981002",this.chatGroupService.selectByPrimaryKey(id).getGroupid());
        if (!result){
            json.setMsg("融云删除群组失败！");
            return json;
        }
        this.chatGroupService.deleteByPrimaryKey(id);
        this.chatGroupUserService.deleteAllByTalkGroupId(id);
        return json;

    }

    @RequestMapping("chatGroupUserListPage")
    public String chatGroupUserListPage(Integer id, HttpServletRequest request){
        request.setAttribute("chatId",id);
        return "chat/chatGroupUserListPage";
    }
    @RequestMapping("chatGroupUserList")
    @ResponseBody
    public Object chatGroupUserList(Map<String,Object> maps, Integer chatId, Integer page, Integer rows, String phone, String remark){
        maps.put("chatId",chatId);
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if (chatId != null){
            map.put("talkGroupId",chatId);
        }
        if (!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if (!StrUtils.isBlank(remark)){
            map.put("remark",remark);
        }
        Grid grid = new Grid();
        grid.setRows(this.chatGroupUserService.selectConditionPaging(map));
        grid.setTotal(this.chatGroupUserService.selectConditionCount(map));
        return grid;
    }

    @RequestMapping("addChatUserPage")
    public String addChatUserPage(Map<String,Object> map, Integer chatId){
        map.put("chatId",chatId);
        return "chat/addChatUserPage";
    }
    @RequestMapping("addChatUser")
    @ResponseBody
    public Object addChatUser(ChatGroupUser chatGroupUser,String phone,Integer isMuted) throws Exception {
        Json json = new Json();
        //验证是否是免打扰状态
        boolean ismuted = true;
        if (isMuted ==0){
            ismuted = false;
        }
        chatGroupUser.setIsmuted(ismuted);
        //验证加入群聊的用户
        User user = userService.selectByPhone(phone);
        if (user == null){
            json.setMsg("此用户不存在");
            return json;
        }
        //获取聊天室
        ChatGroup chatGroup = this.chatGroupService.selectByPrimaryKey(chatGroupUser.getTalkgroupid());
        //融云  用户加入群聊
        boolean reslut = groupRequest.join(chatGroup.getGroupid(),chatGroup.getName(),phone);
        if (!reslut){
            json.setMsg("新用户加入融云失败聊天室失败");
            return json;
        }
        //本地用户加入群聊
       this.chatGroupUserService.insertSelective(chatGroupUser);
        json.setMsg("加入群聊成功了");
        return  json;
    }


    @RequestMapping("bannedChatUserPage")
    public String bannedChatUserPage(Integer id,Map<String,Object> map){
        map.put("id",id);
        return "chat/bannedUserPage";
    }
    @RequestMapping("bannedChatUser")
    @ResponseBody
    public Object bannedChatUser(Integer id,Integer keyname) throws Exception {
        Json json = new Json();
        //不能禁言管理员
        ChatGroupUser chatGroupUser = this.chatGroupUserService.selectByPrimaryKey(id);
        ChatGroup chatGroup = this.chatGroupService.selectByPrimaryKey(chatGroupUser.getTalkgroupid());
        if (chatGroupUser.getRole() == 0){
            json.setMsg("不能禁言管理员");
            return json;
        }
        if (chatGroupUser.getState() == 0){
            json.setMsg("用户已经是禁言状态了");
            return json;
        }
        if (keyname == -1){//永久禁言
            chatGroupUser.setState(0);
            this.chatGroupUserService.updateByPrimaryKeySelective(chatGroupUser);
        }
        //融云禁言
        boolean result = groupRequest.GagAdd(chatGroup.getGroupid(),keyname,userService.selectByPrimaryKey(chatGroupUser.getUserid()).getPhone());
        if (!result){
            json.setMsg("融云禁言群组用户失败");
            return json;
        }
        json.setMsg("禁言了 ："+keyname+" 分钟");
        return json;
    }

    @RequestMapping("leaveChatGroup")
    @ResponseBody
    public Object leaveChatGroup(Integer id) throws Exception {
        Json json = new Json();
        ChatGroupUser chatGroupUser = this.chatGroupUserService.selectByPrimaryKey(id);
        ChatGroup chatGroup = this.chatGroupService.selectByPrimaryKey(chatGroupUser.getTalkgroupid());
        if (chatGroupUser.getRole() == 0){
            json.setMsg("不能移除群主");
            return json;
        }
        User user = this.userService.selectByPrimaryKey(chatGroupUser.getUserid());
        if (user == null){
            json.setMsg("移除的用户不存在");
            return json;
        }
        //融云端，移除群组
        boolean result = groupRequest.quit(chatGroup.getGroupid(),user.getPhone());
        if (!result){
            json.setMsg("融云端，移除群聊失败");
            return json;
        }
        //本地移除群组
        this.chatGroupUserService.deleteByPrimaryKey(id);
        json.setMsg("从群组中移除成功了");
        return json;
    }

    @RequestMapping("permit")
    @ResponseBody
    public Object permit(Integer id) throws Exception {
        Json json = new Json();
        ChatGroupUser chatGroupUser = this.chatGroupUserService.selectByPrimaryKey(id);
        if (chatGroupUser.getState() != 0){
            json.setMsg("此用户状态不是禁言状态");
            return json;
        }
        ChatGroup chatGroup = this.chatGroupService.selectByPrimaryKey(chatGroupUser.getTalkgroupid());
        User user = this.userService.selectByPrimaryKey(chatGroupUser.getUserid());
        //融云 恢复发言
        boolean result = this.groupRequest.GagRemove(chatGroup.getGroupid(),user.getPhone());
        if (!result){
            json.setMsg("融云端，恢复禁言失败了");
        }
        //本地解禁
        chatGroupUser.setState(1);
        this.chatGroupUserService.updateByPrimaryKeySelective(chatGroupUser);
        json.setMsg("恢复正常发言成功了");
        return json;
    }


}
