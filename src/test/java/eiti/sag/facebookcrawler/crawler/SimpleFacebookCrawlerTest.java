package eiti.sag.facebookcrawler.crawler;

import eiti.sag.facebookcrawler.accessor.FacebookAccessor;
import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.visitor.FacebookUserVisitor;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class SimpleFacebookCrawlerTest {

    FacebookAccessor facebookAccessor = mock(FacebookAccessor.class);

    SimpleFacebookCrawler simpleFacebookCrawler = new SimpleFacebookCrawler(facebookAccessor);

    FacebookUserVisitor visitor = mock(FacebookUserVisitor.class);

    String username = "john.smith";

    FacebookUser user = new FacebookUser();

    @Before
    public void setUp() {
        when(visitor.shouldVisit(anyString())).thenReturn(true);
    }

    @Test
    public void shouldVisitUser() {
        when(facebookAccessor.fetchUser(username)).thenReturn(user);

        simpleFacebookCrawler.crawl(username, visitor);

        verify(visitor).visit(user);
    }

    @Test
    public void shouldNotVisitUserIfVisitorSaysSo() {
        when(visitor.shouldVisit(username)).thenReturn(false);

        simpleFacebookCrawler.crawl(username, visitor);

        verify(visitor, never()).visit(user);
    }

    @Test
    public void shouldCallIfExceptionOccurredDuringFetch() {
        Exception exception = new RuntimeException("Fetch failed!");
        when(facebookAccessor.fetchUser(username)).thenThrow(exception);

        simpleFacebookCrawler.crawl(username, visitor);

        verify(visitor).onFetchUserFailed(username, exception);
    }

    @Test
    public void shouldVisitUserAndHisFriendsIfHeHasSome() {
        String friend1 = "john.doe";
        String friend2 = "jane.doe";
        FacebookUser user = userWithFriends(friend1, friend2);
        when(facebookAccessor.fetchUser(username)).thenReturn(user);
        when(facebookAccessor.fetchUser(friend1)).thenReturn(new FacebookUser());

        simpleFacebookCrawler.crawl(username, visitor);

        verify(visitor).shouldVisit(username);
        verify(visitor).shouldVisit(friend1);
        verify(visitor).shouldVisit(friend2);
    }

    @Test
    public void shouldStopCrawlingWhenLimitReached() {
        String friend1 = "john.doe";
        String friend2 = "jane.doe";
        FacebookUser user = userWithFriends(friend1, friend2);
        when(facebookAccessor.fetchUser(username)).thenReturn(user);
        when(facebookAccessor.fetchUser(friend1)).thenReturn(new FacebookUser());

        simpleFacebookCrawler.crawl(username, visitor, 2);

        verify(visitor).shouldVisit(username);
        verify(visitor).shouldVisit(friend1);
        verify(visitor, never()).shouldVisit(friend2);
    }

    private FacebookUser userWithFriends(String... friendIds) {
        FacebookUser user = new FacebookUser();
        user.setFriendsIds(asList(friendIds));
        return user;
    }

}