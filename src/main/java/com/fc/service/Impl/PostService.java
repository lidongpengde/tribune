package com.fc.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fc.async.MessageTask;
import com.fc.mapper.MessageMapper;
import com.fc.mapper.PostMapper;
import com.fc.mapper.ReplyMapper;
import com.fc.mapper.UserMapper;
import com.fc.model.Post;
import com.fc.service.IPostService;
import com.fc.util.MyConstant;
import com.fc.util.MyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class PostService implements IPostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private Jedis jedis;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("${redis.post.list.key}")
    private  String POST_KEY;

    //根据uid，获得帖子列表
    public List<Post> getPostList(int uid) {
        return postMapper.listPostByUid(uid);
    }

    public int publishPost(Post post) {

        //构造帖子
        post.setPublishTime(MyUtil.formatDate(new Date()));
        post.setReplyTime(MyUtil.formatDate(new Date()));
        post.setReplyCount(0);
        post.setLikeCount(0);
        post.setScanCount(0);
        //插入一条帖子记录
        postMapper.insertPost(post);
        System.out.println(post.getPid());
        //更新用户发帖量
        userMapper.updatePostCount(post.getUser().getUid());

        return post.getPid();
    }

    //按时间列出帖子
    public PageInfo<Post> listPostByTime(int pageNum) {
        List<Post> postList =null;
        String result = jedis.get(POST_KEY+pageNum);
        PageInfo<Post> pageInfo =null;
        pageInfo = JSONObject.parseObject(result,new TypeReference<PageInfo<Post>>(){});
        if (result==null){
            PageHelper.startPage(pageNum, 6);
            postList = postMapper.listPostByTime(null);
            pageInfo = new PageInfo<>(postList);
            jedis.set(POST_KEY+pageNum,JSONObject.toJSONString(pageInfo));
            jedis.expire(POST_KEY,60*60*3);

        }
        //分页得到数据列表
        for(Post post : pageInfo.getList()){
            post.setLikeCount((int)(long)jedis.scard(post.getPid()+":like"));
        }
        return pageInfo;
    }

    public Post getPostByPid(int pid) {
        //更新浏览数
        postMapper.updateScanCount(pid);
        Post post =postMapper.getPostByPid(pid);
        //设置点赞数
        long likeCount = jedis.scard(pid+":like");
        post.setLikeCount((int)likeCount);
        return post;
    }

    //点赞
    public String clickLike(int pid, int sessionUid) {
        //pid被sessionUid点赞
        jedis.sadd(pid+":like", String.valueOf(sessionUid));
        //增加用户获赞数
        jedis.hincrBy("vote",sessionUid+"",1);

        //插入一条点赞消息
        taskExecutor.execute(new MessageTask(messageMapper,userMapper,postMapper,replyMapper,pid,0,sessionUid, MyConstant.OPERATION_CLICK_LIKE));
        String result = String.valueOf(jedis.scard(pid+":like"));
        return result;
    }

    //某用户是否赞过某帖子
    public boolean getLikeStatus(int pid, int sessionUid) {
        boolean result = jedis.sismember(pid+":like", String.valueOf(sessionUid));
        return result;
    }
}

