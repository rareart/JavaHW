package UserRecommendations;

import content.Post;

import java.util.List;

public interface UserFeedRecommendations {

    List<Post> getRecommendedPostsByInterest(List<Post> MostLikedPostsByCurrentUser, int from, int to, boolean sortedByDate);

    List<Post> getRecommendedPostsFromTodayMainstream(int from, int to, boolean sortedByDate);

    void MoveToNotInterestingList(Post hiddenPost);

    List<Post> getHiddenPostForCurrentUser(int from, int to, boolean sortedByDate);

}
