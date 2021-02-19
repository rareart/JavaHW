package UserVideos;

import com.company.VideoPlayers.VideoPlayerClass;
import com.company.content.MediaContent;

import java.util.List;

public interface UserVideoPage {

    List<MediaContent> getVideosListForWebPage(int from, int to, boolean sortedByComparator);

    List<MediaContent> getVideosByTitle(String name);

    boolean uploadVideo(MediaContent mediaContent);

    boolean startStream(MediaContent content);

    VideoPlayerClass initPlayerForVideo(String mediaType, String mediaFormat);

    void removeVideo(MediaContent removingVideo);

}
