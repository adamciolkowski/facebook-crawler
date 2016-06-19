package eiti.sag.facebookcrawler.visitor;

import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.repository.FacebookUserRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashSet;
import java.util.Set;

public class SavingFacebookUserVisitor implements FacebookUserVisitor {

    private final FacebookUserRepository repository;

    private final Set<String> failedToFetch = new HashSet<>();

    public SavingFacebookUserVisitor(FacebookUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean shouldVisit(String username) {
        return !repository.contains(username) || !failedToFetch.contains(username);
    }

    @Override
    public void visit(FacebookUser user) {
        repository.save(user);
    }

    @Override
    public void onFetchUserFailed(String username, Exception e) {
        failedToFetch.add(username);
        saveFailure(username);
    }

    private synchronized void saveFailure(String username) {
        File file = new File(System.getProperty("user.home"), "facebook-users-failed");
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(username);
            fw.write('\n');
            fw.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
