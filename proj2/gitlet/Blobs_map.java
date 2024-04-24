package gitlet;

// TODO: any imports you need here
import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;
public class Blobs_map extends HashMap<String , String> implements Serializable{

    public static final File BLOBS_FOLDER = join(Repository.OBJECTS , "blobs");

    public void save_blobs() {
        String blob_id = Repository.sha1_obeject(this);
        File blob_file = join(Commit.MAPS_FOLDER, blob_id);
        writeObject(blob_file, this);
    }

    public static Blobs_map from_file(String commit_id) {
        if (commit_id == null){
            return null;
        }
        File blobs_file = join(Commit.MAPS_FOLDER, commit_id);
        if (blobs_file.exists()) {
            return readObject(blobs_file, Blobs_map.class);
        }
        throw error("No such commit!");
    }

    public void update_blobs() {
        List<String> names_for_addition = plainFilenamesIn(Repository.STAGING_ADD);
        List<String> names_for_removal = plainFilenamesIn(Repository.STAGING_REMOVAL);
        if (names_for_addition == null && names_for_removal == null) {
            throw error("No changes added to the commit.");
        }
        //update the blobs in stage for addition
        if (names_for_addition != null) {
            for (String name_for_addition : names_for_addition) {
                File file_for_addition = join(Repository.STAGING_ADD, name_for_addition);
                String blob_id = readContentsAsString(file_for_addition);
                if (this.get(name_for_addition) == null){
                    this.put(name_for_addition , blob_id);
                }else {
                    this.replace(name_for_addition , blob_id);
                }
                file_for_addition.delete();
            }
        }
        //update the blobs in stage for removal
        if ( names_for_removal != null) {
            for (String name_for_removal : names_for_removal) {
                File file_for_removal = join(Repository.STAGING_REMOVAL, name_for_removal);
                String blob_id = readContentsAsString(file_for_removal);
                this.remove(name_for_removal, blob_id);
                file_for_removal.delete();
            }
        }
    }
}
