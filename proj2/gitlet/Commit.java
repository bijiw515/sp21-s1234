package gitlet;

// TODO: any imports you need here
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level. Combinations of log messages, other metadata (commit date, author, etc.),
 *  a reference to a tree, and references to parent commits.
 *  The repository also maintains a mapping from branch heads to references to commits,
 *  so that certain important commits have symbolic names.
 *  @author Bijiw
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    public static final File COMMITS_FOLDER = join(Repository.OBJECTS , "commits");
    public static final File MAPS_FOLDER = join(Repository.OBJECTS , "maps");
    /**
     * The message of this Commit.
     */
    private String message;
    private String parent;
    private String blobs;
    private Date time_stamp;

    public Commit(String message, String parent, String blobs) {
        this.message = message;
        this.parent = parent;
        this.blobs = blobs;
        if (this.parent == null) {
            this.time_stamp = new Date(0);
        } else {
            this.time_stamp = new Date();
        }
    }

    public String get_parent() {
        return this.parent;
    }

    public Blobs_map get_blobs() {return Blobs_map.from_file(this.blobs);}

    public String get_message(){return this.message;}

    public static boolean is_commit_exist(String commit_id){
        File commit_file = join(COMMITS_FOLDER , commit_id);
        return commit_file.exists();
    }

    public static Commit from_file(String commit_id) {
        if (commit_id == null){
            return null;
        }
        File commit_file = join(COMMITS_FOLDER, commit_id);
        if (commit_file.exists()) {
            return readObject(commit_file, Commit.class);
        }
        throw error("No such commit!");
    }

    public void save_commit() {
        String commit_id = Repository.sha1_obeject(this);
        File commit_file = join(COMMITS_FOLDER, commit_id);
        writeObject(commit_file, this);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z" , Locale.US);
        String formattedDate = sdf.format(this.time_stamp);
        return String.format("===\ncommit %s\nDate: %s\n%s\n"
                , Repository.sha1_obeject(this)
                , formattedDate, this.message);
    }
}