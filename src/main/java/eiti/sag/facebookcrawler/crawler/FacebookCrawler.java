package eiti.sag.facebookcrawler.crawler;

import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;

import java.util.Collection;

public interface FacebookCrawler {

    void crawl(String username, FacebookUserVisitor visitor);

    void crawl(Collection<String> usernames, FacebookUserVisitor visitor);

    void crawl(String username, FacebookUserVisitor visitor, int maxUsers);

}
