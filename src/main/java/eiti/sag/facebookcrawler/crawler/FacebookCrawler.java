package eiti.sag.facebookcrawler.crawler;

import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;

public interface FacebookCrawler {

    void crawl(String username, FacebookUserVisitor visitor);

    void crawl(String username, FacebookUserVisitor visitor, int maxUsers);

}
