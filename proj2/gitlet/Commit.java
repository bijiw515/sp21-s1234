package gitlet;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;


/** Represents a gitlet commit object.
 *  does at a high level. Combinations of log messages, other metadata (commit date, author, etc.),
 *  a reference to a tree, and references to parent commits.
 *  The repository also maintains a mapping from branch heads to references to commits,
 *  so that certain important commits have symbolic names.
 *  @author Bijiw
 */
public class Commit implements Serializable {
    /**
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
    private String merged_in_parent;
    private String blobs;
    private Date time_stamp;

    public Commit(String message, String parent,String merged_in_parent , String blobs) {
        this.message = message;
        this.parent = parent;
        this.merged_in_parent = merged_in_parent;
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
    public String get_merged_in_parent(){
        return this.merged_in_parent;
    }

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
        if (this.merged_in_parent != null){
            String substring_parent = this.parent.substring(0 , 7);
            String substring_sec_parent = this.merged_in_parent.substring(0 , 7);
            return String.format("===\ncommit %s\nMerge: %s %s\nDate: %s\n%s\n"
                    , Repository.sha1_obeject(this)
                    , substring_parent
                    , substring_sec_parent
                    , formattedDate,
                    this.message);
        }
        return String.format("===\ncommit %s\nDate: %s\n%s\n"
                , Repository.sha1_obeject(this)
                , formattedDate, this.message);
    }
}