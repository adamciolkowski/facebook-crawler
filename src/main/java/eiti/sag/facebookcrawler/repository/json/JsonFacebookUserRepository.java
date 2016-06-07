package eiti.sag.facebookcrawler.repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eiti.sag.facebookcrawler.model.FacebookUser;
import eiti.sag.facebookcrawler.repository.FacebookUserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

public class JsonFacebookUserRepository implements FacebookUserRepository {

    private final File directory;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public JsonFacebookUserRepository(File directory) {
        this.directory = directory;
        directory.mkdirs();
    }

    @Override
    public void save(FacebookUser user) {
        File file = fileFor(user.getUsername());
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file))) {
            pw.write(gson.toJson(user));
            pw.flush();
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean contains(String username) {
        return fileFor(username).exists();
    }

    @Override
    public FacebookUser find(String username) {
        return readUser(fileFor(username));
    }

    @Override
    public Collection<FacebookUser> findAll() {
        File[] files = directory.listFiles();
        if(files == null)
            return emptyList();
        return readUsers(files);
    }

    private Collection<FacebookUser> readUsers(File[] files) {
        List<FacebookUser> users = new ArrayList<>();
        for (File file : files)
            users.add(readUser(file));
        return users;
    }

    private FacebookUser readUser(File file) {
        try {
            Reader reader = toReader(file);
            return gson.fromJson(reader, FacebookUser.class);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    private File fileFor(String username) {
        return new File(directory, username + ".json");
    }

    private Reader toReader(File file) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

}
