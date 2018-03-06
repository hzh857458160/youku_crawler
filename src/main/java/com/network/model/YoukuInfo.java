package com.network.model;

import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 优酷视频信息的model类
 * @author He Zhenghan
 *
 */
@Entity
public class YoukuInfo {

	@Id
	@GeneratedValue
	private Integer id;				//视频在数据库中的id

	private String videoUrl;		//视频url
	private String videoName;		//视频名称
	private String videoType;		//视频类型
	private String videoIntro;		//视频简介
	private String videoIntroUrl;	//视频简介获取url（可能为空）
	private String releaseTime;		//发布时间
	private String authorName;		//作者昵称
	private Integer playTimes;		//播放次数
	private Integer likeTimes;		//点赞次数
	private Integer dislikeTimes;	//点踩次数
	//可能用到的属性
	private String downloadUrl;		//视频下载的url
	private String videoTheme;		//视频主题

	@Override
	public String toString() {
		return 	"*********************************"
				+	"\nvideoUrl: "	+ videoUrl
				+	"\nvideoName:"	+ videoName		+ "    videoType:"	+ videoType
				+	"\nvideoIntro:"	+ videoIntro
				+	"\nreleaseTime:"+ releaseTime	+ "    authorName:"	+ authorName	+ "    playTimes:"	+ playTimes
				+	"\nlike:"		+ likeTimes		+ "    dislike:"	+ dislikeTimes	+ "\n"
				+"*********************************";

	}


	public String postParameter(int opcode) {

		if (opcode == 0) {
			return "videoUrl,videoName,videoType,videoIntro,videoIntroUrl,releaseTime,authorName,playTimes"
					+ ",likeTimes,dislikeTimes,downloadUrl,videoTheme";
		} else if (opcode == 1) {
			return videoUrl +
					"," + videoName +
					"," + videoType +
					"," + videoIntro +
					"," + videoIntroUrl +
					"," + releaseTime +
					"," + authorName +
					"," + playTimes +
					"," + likeTimes +
					"," + dislikeTimes +
					"," + downloadUrl +
					"," + videoTheme;
		}else{
			return null;
		}
	}




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getVideoIntro() {
		return videoIntro;
	}

	public void setVideoIntro(String videoIntro) {
		this.videoIntro = videoIntro;
	}

	public String getVideoIntroUrl() {
		return videoIntroUrl;
	}

	public void setVideoIntroUrl(String videoIntroUrl) {
		this.videoIntroUrl = videoIntroUrl;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(Integer playTimes) {
		this.playTimes = playTimes;
	}

	public Integer getLikeTimes() {
		return likeTimes;
	}

	public void setLikeTimes(Integer likeTimes) {
		this.likeTimes = likeTimes;
	}

	public Integer getDislikeTimes() {
		return dislikeTimes;
	}

	public void setDislikeTimes(Integer dislikeTimes) {
		this.dislikeTimes = dislikeTimes;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getVideoTheme() {
		return videoTheme;
	}

	public void setVideoTheme(String videoTheme) {
		this.videoTheme = videoTheme;
	}






	
	
	
	
}
