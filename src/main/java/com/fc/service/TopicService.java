package com.fc.service;

import com.fc.mapper.PostMapper;
import com.fc.mapper.TopicMapper;
import com.fc.mapper.UserMapper;
import com.fc.model.MessageResult;
import com.fc.model.Post;
import com.fc.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TopicService {


    @Autowired
    private TopicMapper topicMapper;

    public List<Topic> listTopic() {
        return topicMapper.listTopic();
    }

    public List<String> listImage() {
        return topicMapper.listImage();
    }

    public MessageResult<Integer> addTopic(Topic topic) {
        MessageResult<Integer> result =new MessageResult<>();
        Integer cc = topicMapper.addTopic(topic);
        if (cc>0){
            result.setBuessObj(cc);
            result.setSuccess(true);
            return result;
        }else{
            result.setSuccess(true);
        }
        return result;
    }
}

