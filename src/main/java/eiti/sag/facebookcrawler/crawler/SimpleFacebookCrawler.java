package eiti.sag.facebookcrawler.crawler;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class SimpleFacebookCrawler implements FacebookCrawler {

    private final FacebookAccessor accessor;

    public SimpleFacebookCrawler(FacebookAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public void crawl(String username, FacebookUserVisitor visitor) {
        crawl(username, visitor, Integer.MAX_VALUE);
    }

    @Override
    public void crawl(String username, FacebookUserVisitor visitor, int count) {
        Queue<String> unvisited = new LinkedList<>();
        unvisited.add(username);
        crawl(unvisited, visitor, count);
    }

    private void crawl(Queue<String> unvisited, FacebookUserVisitor visitor, int count) {
        int remaining = count;
        while (!unvisited.isEmpty() && remaining > 0) {
            if(process(unvisited, visitor))
                remaining--;
        }
    }

    private boolean process(Queue<String> unvisited, FacebookUserVisitor visitor) {
        String username = unvisited.remove();
        if(visitor.shouldVisit(username))
            return process(username, visitor, unvisited);
        return false;
    }

    private boolean process(String username, FacebookUserVisitor visitor, Queue<String> unvisited) {
        try {
            tryProcess(username, visitor, unvisited);
            return true;
        } catch (Exception e) {
            visitor.onFetchUserFailed(username, e);
            return false;
        }
    }

    private void tryProcess(String username, FacebookUserVisitor visitor, Queue<String> unvisited) {
        FacebookUser user = accessor.fetchUser(username);
        visitor.visit(user);
        enqueueFriendsIds(unvisited, user);
    }

    private void enqueueFriendsIds(Queue<String> unvisited, FacebookUser user) {
        Collection<String> friendsIds = user.getFriendsIds();
        if(friendsIds != null)
            unvisited.addAll(friendsIds);
    }

}
