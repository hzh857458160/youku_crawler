package com.network.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 优酷视频信息的model类
 * @author He Zhenghan
 *
 */
@Entity
@Data
@ToString
public class YoukuInfo {

	@Id
	@GeneratedValue
	private Long id;                //视频在数据库中的id

	private String videoUrl;		//视频url
	private String videoName;		//视频名称
	private String videoIntro;		//视频简介
	private String releaseTime;		//发布时间
	private String authorName;		//作者昵称
	private Integer playTimes;		//播放次数
	private Integer likeTimes;		//点赞次数
	private Integer dislikeTimes;	//点踩次数
	
}
