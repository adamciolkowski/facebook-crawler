package eiti.sag.facebookcrawler;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.accessor.jsoup.FacebookAuthCookies;
import eiti.sag.facebookcrawler.accessor.jsoup.JsoupFacebookAccessor;
import eiti.sag.facebookcrawler.crawler.FacebookCrawler;
import eiti.sag.facebookcrawler.crawler.SimpleFacebookCrawler;
import eiti.sag.facebookcrawler.repository.FacebookUserRepository;
import eiti.sag.facebookcrawler.repository.json.JsonFacebookUserRepository;
import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;
import eiti.sag.facebookcrawler.visitor.SavingFacebookUserVisitor;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if(args.length != 4)
            throw new IllegalArgumentException("No Facebook c_user, datr, xs cookies or username given");
        FacebookAuthCookies cookies = new FacebookAuthCookies(args[0], args[1], args[2]);
        new Main().run(cookies, args[3]);
    }

    private void run(FacebookAuthCookies cookies, String username) {
        FacebookAccessor accessor = new JsoupFacebookAccessor(cookies);
        File directory = new File(System.getProperty("user.home"), "facebook-users");
        FacebookUserRepository repository = new JsonFacebookUserRepository(directory);

        FacebookUserVisitor visitor = new SavingFacebookUserVisitor(repository);
        FacebookCrawler crawler = new SimpleFacebookCrawler(accessor);
        crawler.crawl(username, visitor, 100);
    }

}
