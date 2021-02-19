package UserFeed;

import content.Post;

import java.util.List;

public interface UserFeedPage {

    List<Post> getPostsListForWebPage(int from, int to, boolean sortedByDate);

    List<Post> getPostsFromSelectedPosters(int from, int to, boolean sortedByDate, String postersName);

    List<Post> getPostsListForWebPageWithImg(int from, int to, boolean sortedByDate);

    List<Post> getPostsListForWebPageWithAudio(int from, int to, boolean sortedByDate);

    List<Post> getPostsListForWebPageWithVideo(int from, int to, boolean sortedByDate);

    List<Post> getPostsListForWebPageWithComments(int from, int to, boolean sortedByDate, int numOfComments);

    List<Post> getMostLikedPostsByCurrentUser();

    boolean createNewPost(String title, String text);

    void deletePost(Post delPost);

}
