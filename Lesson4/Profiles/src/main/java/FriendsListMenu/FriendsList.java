package FriendsListMenu;

import com.company.UserProfile.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FriendsList {

    private final List<Profile> userFriendList;

    public FriendsList(Profile self){
        this.userFriendList = self.getFriendsList();
    }

    public List<Profile> getUserRawFriendList() {
        return new ArrayList<>(userFriendList);
    }

    public List<Profile> searchByName(String name){
        List<Profile> matchedNames = new ArrayList<>();
        for(Profile profile: userFriendList){
            if(profile.getName().equals(name)){
                matchedNames.add(profile);
            }
        }
        return matchedNames;
    }

    public List<Profile> searchByDateOfBirth(Date dateOfBirth){
        List<Profile> matchedDates = new ArrayList<>();
        for(Profile profile: userFriendList){
            if(profile.getDateOfBirth().equals(dateOfBirth)){
                matchedDates.add(profile);
            }
        }
        return matchedDates;
    }

    public void removeGroupOfFriends(List <Profile> removingFriends){
        for(Profile profile: removingFriends){
            userFriendList.remove(profile);
        }
    }

    public List<Profile> getFriendsListForWebPage(int from, int to, boolean sortedByName){
        if(sortedByName){
            List<Profile> sortedList = new ArrayList<>(userFriendList);
            Collections.sort(sortedList);
            return new ArrayList<>(sortedList.subList(from, to));
        }
        return new ArrayList<>(userFriendList.subList(from, to));
    }
}
