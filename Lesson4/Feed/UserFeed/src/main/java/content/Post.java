package content;

import com.company.MusicPlayers.MusicPlayerClass;
import com.company.PhotoViewers.PhotoViewerClass;
import com.company.UserProfile.Profile;
import com.company.VideoPlayers.VideoPlayerClass;
import com.company.content.MediaContent;
import com.company.content.StaticContent;

import java.util.*;

public class Post implements Comparable<Post> {

    private List<Profile> likes;
    private Map<Profile, String> comments;
    private List<MediaContent> attachedMediaContent;
    private List<StaticContent> attachedStaticContent;

    PhotoViewerClass photoViewer;
    MusicPlayerClass musicPlayer;
    VideoPlayerClass videoPlayer;

    private final Profile poster;
    private String title;
    private final Date dateOfCreation;
    private String text;

    public Post(Profile poster, String title, String text){
        this.poster = poster;
        this.title = title;
        dateOfCreation = new java.util.Date();
        this.text = text;
    }

    public String getPostersName() {
        return poster.getName();
    }

    public StaticContent getPostersPhoto(){
        return poster.getPhoto();
    }

    public Date getDateOfCreation() {
        return (Date) dateOfCreation.clone();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void showImages(){
        if(attachedStaticContent == null){
            System.out.println("Error: nothing to show");
        } else {
            isPhotoViewerExist();
            for(StaticContent content : attachedStaticContent){
                photoViewer.show(content);
            }
        }
    }

    public void hideImages(){
        if(attachedStaticContent == null){
            System.out.println("Error: nothing to hide");
        } else {
            isPhotoViewerExist();
            for(StaticContent content : attachedStaticContent){
                photoViewer.hide(content);
            }
        }
    }

    public void addImages(StaticContent img){
        isPhotoViewerExist();
        if(photoViewer.isSupported(img.getContentType(), img.getContentFormat())){
            isAttachedStaticContentExist();
            attachedStaticContent.add(img);
        }
    }

    public void audioControl(char firstLetter, int indexOfMedia){
        if(attachedMediaContent == null){
            System.out.println("Error: nothing to control");
        } else {
            isMusicPlayerExist();
            if(indexOfMedia<attachedMediaContent.size()) {
                switch (firstLetter) {
                    case 'p':
                        musicPlayer.play(attachedMediaContent.get(indexOfMedia));
                        break;
                    case 's':
                        musicPlayer.stop(attachedMediaContent.get(indexOfMedia));
                        break;
                    case 'f':
                        musicPlayer.forward(attachedMediaContent.get(indexOfMedia), 10);
                        break;
                    case 'r':
                        musicPlayer.reverse(attachedMediaContent.get(indexOfMedia), 10);
                        break;
                    default:
                        System.out.println("Error! incorrect args: p - play, s - stop, f - forward, r - reverse");
                }
            } else {
                System.out.println("Error! incorrect index of media");
            }
        }
    }

    public void addAudio(MediaContent audio){
        isMusicPlayerExist();
        if(musicPlayer.isSupported(audio.getContentType(), audio.getContentFormat())){
            isAttachedMediaContentExist();
            attachedMediaContent.add(audio);
        }
    }

    public void videoControl(char firstLetter, int indexOfMedia){
        if(attachedMediaContent == null){
            System.out.println("Error: nothing to control");
        } else {
            isVideoPlayerExist();
            if(indexOfMedia<attachedMediaContent.size()) {
                switch (firstLetter) {
                    case 'p':
                        videoPlayer.play(attachedMediaContent.get(indexOfMedia));
                        break;
                    case 's':
                        videoPlayer.stop(attachedMediaContent.get(indexOfMedia));
                        break;
                    case 'f':
                        videoPlayer.forward(attachedMediaContent.get(indexOfMedia), 15);
                        break;
                    case 'r':
                        videoPlayer.reverse(attachedMediaContent.get(indexOfMedia), 15);
                        break;
                    default:
                        System.out.println("Error! incorrect args: p - play, s - stop, f - forward, r - reverse");
                }
            } else {
                System.out.println("Error! incorrect index of media");
            }
        }
    }

    public void addVideo(MediaContent video){
        isVideoPlayerExist();
        if(videoPlayer.isSupported(video.getContentType(), video.getContentFormat())){
            isAttachedMediaContentExist();
            attachedMediaContent.add(video);
        }
    }


    public void like(Profile liker){
        if (likes == null){
            likes = new ArrayList<>();
        }
        likes.add(liker);
    }

    public List<Profile> getLikes(){
        if (likes == null){
            return null;
        }
        return new ArrayList<>(likes);
    }

    public void setComment(Profile commentator, String text){
        if (comments == null){
            comments = new LinkedHashMap<>();
        }
        comments.put(commentator, text);
    }

    public Map<Profile, String> getComments(){
        if (comments == null){
            return null;
        }
        return new LinkedHashMap<>(comments);
    }

    private void isAttachedStaticContentExist(){
        if (attachedStaticContent == null) {
            attachedStaticContent = new ArrayList<>();
        }
    }

    private void isAttachedMediaContentExist(){
        if (attachedMediaContent == null) {
            attachedMediaContent = new ArrayList<>();
        }
    }

    private void isPhotoViewerExist(){
        if (photoViewer == null) {
            photoViewer = new PhotoViewerClass();
        }
    }

    private void isMusicPlayerExist(){
        if (musicPlayer == null) {
            musicPlayer = new MusicPlayerClass();
        }
    }

    private void isVideoPlayerExist(){
        if (videoPlayer == null) {
            videoPlayer = new VideoPlayerClass();
        }
    }

    @Override
    public int compareTo(Post o) {
        return o.getDateOfCreation().compareTo(this.dateOfCreation);
    }
}
