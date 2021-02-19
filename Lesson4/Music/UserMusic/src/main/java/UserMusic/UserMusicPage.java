package UserMusic;

import com.company.MusicPlayers.MusicPlayerClass;
import com.company.content.MediaContent;

import java.util.List;

public interface UserMusicPage {

    List<MediaContent> getMusicListForWebPage(int from, int to, boolean sortedByComparator);

    List<MediaContent> getMusicByTitle(String name);

    List<MediaContent> shufflePlaylist(List<MediaContent> playlist);

    boolean uploadMusic(MediaContent mediaContent);

    boolean startRadioStream(MediaContent mediaContent);

    MusicPlayerClass initPlayerForMusic(String mediaType, String mediaFormat);

    void removeMusic(MediaContent removingMusic);
}
