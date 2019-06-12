package com.fc.controller;

import com.fc.model.MessageResult;
import com.fc.model.Topic;
import com.fc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 列出所有话题
     * @param model
     * @return
     */
    @RequestMapping("/listTopic.do")
    public String listTopic(Model model){
        List<Topic> topicList = topicService.listTopic();
        model.addAttribute("topicList",topicList);
        return "topic";
    }

    @RequestMapping("/listImage.do")
    public String listImage(Model model){
        List<String> imageList = topicService.listImage();
        model.addAttribute("imageList",imageList);
        return "image";
    }
    @RequestMapping("/addTopic.do")
    @ResponseBody
    public Object addTopic(Topic topic){
        MessageResult<Integer> result =new MessageResult<>();
        try {
            result =topicService.addTopic(topic);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("话题保存失败");
        }
        return result;
    }
}





