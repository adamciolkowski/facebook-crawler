package eiti.sag.facebookcrawler;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;

import java.util.LinkedList;
import java.util.Queue;

public class FacebookCrawler {

    private final FacebookAccessor accessor;
    private final FacebookUserVisitor visitor;

    private final Queue<String> unvisited;

    public FacebookCrawler(FacebookAccessor accessor, FacebookUserVisitor visitor) {
        this(accessor, visitor, new LinkedList<>());
    }

    public FacebookCrawler(FacebookAccessor accessor, FacebookUserVisitor visitor, Queue<String> queue) {
        this.accessor = accessor;
        this.visitor = visitor;
        unvisited = queue;
    }

    public void loadRecursively(String username) {
        unvisited.add(username);
        while (!unvisited.isEmpty())
            visit(unvisited.remove());
    }

    private void visit(String username) {
        if(!visitor.shouldVisit(username))
            return;
        doVisit(username);
    }

    private void doVisit(String username) {
        try {
            tryVisit(username);
        } catch (Exception e) {
            visitor.onFetchUserFailed(username, e);
        }
    }

    private void tryVisit(String username) {
        FacebookUser user = accessor.fetchUser(username);
        visitor.visit(user);
        unvisited.addAll(user.getFriendsIds());
    }
}
