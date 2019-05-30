package com.fc.controller;

import com.fc.model.MessageResult;
import com.fc.model.PageBean;
import com.fc.model.Post;
import com.fc.model.User;
import com.fc.service.Impl.PostService;
import com.fc.service.UserService;
import com.fc.util.MyConstant;
import com.fc.service.QiniuService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private QiniuService qiniuService;

    /**
     * 去主页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(Model model, HttpServletRequest request){
        //列出用户
        List<User> userList = userService.listUserByTime();
        //列出活跃用户
        List<User> hotUserList = userService.listUserByHot();
        //列出帖子
        PageInfo<Post> pageBean = postService.listPostByTime(1);
        //向模型中添加数据
        model.addAttribute("pageBean",pageBean);
        model.addAttribute("userList",userList);
        model.addAttribute("hotUserList",hotUserList);
        return "index";
    }


    //上传图片
    @RequestMapping(value = "/upload.do", method = {RequestMethod.POST})
    public
    @ResponseBody
    Object upload(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        MessageResult<String> messageResult = new MessageResult();
        // 文件类型限制
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png"};
        boolean allowed = Arrays.asList(allowedType).contains(file.getContentType());
        if (!allowed) {
            return "error|不支持的类型";
        }
        // 图片大小限制
        if (file.getSize() > 3 * 1024 * 1024) {
            return "error|图片大小不能超过3M";
        }
        // 包含原始文件名的字符串
        String fi = file.getOriginalFilename();
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
        // 生成云端的真实文件名
        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;

        qiniuService.upload(file.getBytes(), remoteFileName);

        messageResult.setBuessObj(MyConstant.QINIU_IMAGE_URL + remoteFileName);
        messageResult.setSuccess(true);

        // 返回图片的URL地址
        return messageResult;
    }
}
