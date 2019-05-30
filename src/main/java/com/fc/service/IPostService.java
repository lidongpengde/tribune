package com.fc.service;

import com.fc.model.PageBean;
import com.fc.model.Post;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by lidp on 2019/5/22.
 */
public interface IPostService {
     List<Post> getPostList(int uid);

     int publishPost(Post post);

    //按时间列出帖子
    PageInfo<Post> listPostByTime(int pageNum);

     Post getPostByPid(int pid);

    //点赞
     String clickLike(int pid, int sessionUid);

    //某用户是否赞过某帖子
     boolean getLikeStatus(int pid, int sessionUid);
}
