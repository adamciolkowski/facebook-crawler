package eiti.sag.facebookcrawler.repository;

import eiti.sag.facebookcrawler.model.FacebookUser;

import java.util.Collection;

public interface FacebookUserRepository {

    void save(FacebookUser user);

    boolean contains(String username);

    FacebookUser find(String username);

    Collection<FacebookUser> findAll();
}
