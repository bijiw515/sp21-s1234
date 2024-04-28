package gitlet;

import edu.princeton.cs.algs4.SET;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Bijiw
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File OBJECTS = join(GITLET_DIR, "objects");
    public static final File STAGING_AREA = join(GITLET_DIR, "staging_area");
    public static final File STAGING_ADD = join(STAGING_AREA, "staging_add");
    public static final File STAGING_REMOVAL = join(STAGING_AREA, "staging_removal");
    public static final File BRANCH_FOLDER = join(GITLET_DIR, "branches");

    private static void init_folders() {
        GITLET_DIR.mkdir();
        OBJECTS.mkdir();
        STAGING_AREA.mkdir();
        STAGING_ADD.mkdir();
        STAGING_REMOVAL.mkdir();
        BRANCH_FOLDER.mkdir();
        Commit.COMMITS_FOLDER.mkdir();
        Commit.MAPS_FOLDER.mkdir();
        Blobs_map.BLOBS_FOLDER.mkdir();
    }

    private static String get_Head_id() {
        File HEAD = join(GITLET_DIR, "HEAD");
        String HEAD_branch_name = readContentsAsString(HEAD);
        File HEAD_branch = join(BRANCH_FOLDER, HEAD_branch_name);
        return readContentsAsString(HEAD_branch);
    }

    private static String get_Head_name() {
        File HEAD = join(GITLET_DIR, "HEAD");
        return readContentsAsString(HEAD);
    }

    private static String get_specified_branch(String branch_name) {
        File branch = join(BRANCH_FOLDER, branch_name);
        return readContentsAsString(branch);
    }

    private static String get_split_point(){
        File split_point = join(GITLET_DIR , "split_point");
        return readContentsAsString(split_point);
    }

    private static void remove_split_point(){
        File split_point = join(GITLET_DIR , "split_point");
        if (split_point.exists()){
            split_point.delete();
        }
    }

    private static void update_Head(String branch_name) {
        File HEAD = join(GITLET_DIR, "HEAD");
        writeContents(HEAD, branch_name);
    }

    private static void update_current_branch(String branch_id) {
        String current_branch_name = get_Head_name();
        File current_branch = join(BRANCH_FOLDER, current_branch_name);
        writeContents(current_branch, branch_id);
    }

    public static String sha1_obeject(Serializable object) {
        byte[] serielized_object = serialize(object);
        return sha1((Object) serielized_object);
    }

    public static String sha1_file(String file_name) {
        File given_file = join(CWD, file_name);
        String given_content = readContentsAsString(given_file);
        return sha1((Object) given_content);
    }

    private static Commit get_current_commit() {
        String HEAD_id = get_Head_id();
        return Commit.from_file(HEAD_id);
    }

    private static String get_content_from_blob(String blob_id) {
        File blob_file = join(Blobs_map.BLOBS_FOLDER, blob_id);
        return readContentsAsString(blob_file);
    }

    private static void clear_staging_area() {
        //update the blobs in stage for addition
        List<String> names_for_addition = plainFilenamesIn(Repository.STAGING_ADD);
        if (! names_for_addition.isEmpty()) {
            for (String name_for_addition : names_for_addition) {
                File file_for_addition = join(Repository.STAGING_ADD, name_for_addition);
                file_for_addition.delete();
            }
        }
        //update the blobs in stage for removal
        List<String> names_for_removal = plainFilenamesIn(Repository.STAGING_REMOVAL);
        if (! names_for_removal.isEmpty()) {
            for (String name_for_removal : names_for_removal) {
                File file_for_removal = join(Repository.STAGING_ADD, name_for_removal);
                file_for_removal.delete();
            }
        }
    }

    private static void validate_CWD_untracks_curr_commit() {
        Commit current_commit = get_current_commit();
        Blobs_map current_blobs = current_commit.get_blobs();
        List<String> file_names = plainFilenamesIn(CWD);
        if (file_names == null) {
            if (!current_blobs.isEmpty()) {
                throw error("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
        if (file_names.size() != current_blobs.size()) {
            throw error("There is an untracked file in the way; delete it, or add and commit it first.");
        }
        for (String name : file_names) {
            String tracked_blob_id = current_blobs.get(name);
            String current_blob_id = sha1_file(name);
            if (!tracked_blob_id.equals(current_blob_id)) {
                throw error("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
    }

    /**
     * Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit:
     * a commit that contains no files and has the commit message initial commit
     * (just like that, with no punctuation). It will have a single branch: master,
     * which initially points to this initial commit, and master will be the current branch.
     * The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970
     * in whatever format you choose for dates (this is called “The (Unix) Epoch”, represented internally by the time 0.)
     * Since the initial commit in all repositories created by Gitlet will have exactly the same content,
     * it follows that all repositories will automatically share this commit (they will all have the same UID)
     * and all commits in all repositories will trace back to it.
     */
    public static void init() throws IOException {
        if (GITLET_DIR.exists()) {
            throw error("A Gitlet version-control system already exists in the current directory.");
        }
        //init the folders
        init_folders();
        Blobs_map initial_blobs = new Blobs_map();
        String initial_blobs_id = sha1_obeject(initial_blobs);
        initial_blobs.save_blobs();
        //set the first commit
        Commit initial_commit = new Commit("initial commit", null,null , initial_blobs_id);
        initial_commit.save_commit();
        String initial_commit_id = sha1_obeject(initial_commit);
        //set the HEAD and master
        update_Head("master");
        update_current_branch(initial_commit_id);
    }

    /**
     * Adds a copy of the file as it currently exists to the staging area
     * (see the description of the commit command).
     * For this reason, adding a file is also called staging the file for addition.
     * Staging an already-staged file overwrites the previous entry in the staging area with the new contents.
     * The staging area should be somewhere in .gitlet.
     * If the current working version of the file is identical
     * to the version in the current commit, do not stage it to be added,
     * and remove it from the staging area if it is already there
     * (as can happen when a file is changed, added, and then changed back to it’s original version).
     * The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command.
     */

    public static void add_file(String file_name) {
        File file_to_add = join(CWD, file_name);
        if (!file_to_add.exists()) {
            throw error("File does not exist.");
        }
        String given_content = readContentsAsString(file_to_add);
        String given_id = sha1((Object) given_content);
        //get the current commit and current blobs
        Commit current_commit = get_current_commit();
        Map<String, String> current_blobs = current_commit.get_blobs();
        //as can happen when a file is changed, added, and then changed back to it’s original version
        File file_to_commit = join(STAGING_ADD, file_name);
        if (file_to_commit.exists() && Objects.equals(current_blobs.get(file_name), given_id)) {
            file_to_commit.delete();
        }
        //add the file to the staging area
        if (current_blobs.isEmpty() || !Objects.equals(current_blobs.get(file_name), given_id)) {
            File blob = join(Blobs_map.BLOBS_FOLDER, given_id);
            writeContents(blob, (Object) given_content);
            writeContents(file_to_commit, (Object) given_id);
        }
    }

    private static void copy_the_blobs(Map<String, String> new_Blobs, Map<String, String> parent_Blobs) {
        new_Blobs.putAll(parent_Blobs);
    }

    /**
     * Saves a snapshot of tracked files in the current commit and staging area
     * so they can be restored at a later time, creating a new commit.
     **/
    public static void make_commit(String message , String merged_in_parent) {
        //copy the parent commit
        String HEAD_id = get_Head_id();
        Commit parent_commit = get_current_commit();
        //copy the parent blobs
        Map<String, String> parent_blobs = parent_commit.get_blobs();
        Blobs_map new_blobs = new Blobs_map();
        copy_the_blobs(new_blobs, parent_blobs);
        new_blobs.update_blobs();
        String new_blobs_id = sha1_obeject(new_blobs);
        new_blobs.save_blobs();
        //create new commit
        Commit new_commit = new Commit(message, HEAD_id, merged_in_parent , new_blobs_id);
        new_commit.save_commit();
        //reset the HEAD and current branch
        String new_commit_id = sha1_obeject(new_commit);
        update_current_branch(new_commit_id);
    }

    /**
     * Unstage the file if it is currently staged for addition.
     * If the file is tracked in the current commit,
     * stage it for removal and remove the file from the working directory
     * if the user has not already done so
     * (do not remove it unless it is tracked in the current commit).
     **/
    public static void remove_file(String file_name) {
        //relating files
        File file_to_delete = join(CWD, file_name);
        File file_to_remove = join(STAGING_REMOVAL, file_name);
        File file_to_unstage = join(STAGING_ADD, file_name);
        //get the current commit and the specified file(blob)
        Commit current_commit = get_current_commit();
        Map<String, String> current_blobs = current_commit.get_blobs();
        String remove_id = current_blobs.get(file_name);
        //unstage the specified file(blob)
        if (file_to_unstage.exists()) {
            file_to_unstage.delete();
        }
        //stage for removal and delete the specified file in the working directory
        if (remove_id != null) {
            writeContents(file_to_remove, remove_id);
            restrictedDelete(file_to_delete);
        }
    }

    /**
     * starting at the current head commit, display information
     * about each commit backwards along the commit tree until
     * the initial commit, following the first parent commit links
     * , ignoring any second parents found in merge commits.
     * (In regular Git, this is what you get with git log --first-parent).
     * This set of commit nodes is called the commit’s history.
     * For every node in this history, the information it should
     * display is the commit id, the time the commit was made, and the commit
     */
    public static void log() {
        Commit current_commit = get_current_commit();
        String current_commit_id = sha1_obeject(current_commit);
        while (current_commit_id != null) {
            System.out.println(current_commit);
            current_commit_id = current_commit.get_parent();
            current_commit = Commit.from_file(current_commit_id);
        }
    }

    /**
     * Like log, except displays information about all commits ever made.
     * The order of the commits does not matter.
     **/
    public static void global_log() {
        List<String> ids_commits = plainFilenamesIn(Commit.COMMITS_FOLDER);
        for (String commit_id : ids_commits) {
            Commit current_commit = Commit.from_file(commit_id);
            System.out.println(current_commit);
        }
    }

    /**
     * Prints out the ids of all commits that have the given commit message,
     * one per line. If there are multiple such commits, it prints the ids out
     * on separate lines. The commit message is a single operand; to indicate a
     * multiword message, put the operand in quotation marks, as for the commit command below.
     **/
    public static void find(String message) {
        List<String> ids_commits = plainFilenamesIn(Commit.COMMITS_FOLDER);
        if (ids_commits == null) {
            throw error("Found no commit with that message.");
        }
        boolean have_such_commit = false;
        for (String sha1_commit : ids_commits) {
            Commit current_commit = Commit.from_file(sha1_commit);
            if (current_commit.get_message().equals(message)) {
                System.out.println(current_commit);
                have_such_commit = true;
            }
        }
        if (!have_such_commit) {
            throw error("Found no commit with that message.");
        }
    }

    /**
     * Displays what branches currently exist, and marks the current branch with a *.
     * Also displays what files have been staged for addition or removal. An example
     * of the exact format it should follow is as follows.
     **/
    public static void print_status() {
        //list out the branches
        String HEAD_branch_name = get_Head_name();
        System.out.println(String.format("=== Branches ===\n*%s\n", HEAD_branch_name));
        List<String> branches_name = plainFilenamesIn(BRANCH_FOLDER);
        for (String branch_name : branches_name) {
            if (branch_name.equals(HEAD_branch_name)) {
                continue;
            }
            System.out.println(branch_name);
        }
        //list out the stage for addition
        System.out.println("=== Staged Files ===");
        List<String> file_names_addition = plainFilenamesIn(STAGING_ADD);
        if (file_names_addition != null) {
            for (String file_name_addition : file_names_addition) {
                System.out.println(file_name_addition);
            }
        }
        //list out the stage for removal
        System.out.println("=== Removed Files ===");
        List<String> file_names_removal = plainFilenamesIn(STAGING_ADD);
        if (file_names_addition != null) {
            for (String file_name_removal : file_names_removal) {
                System.out.println(file_name_removal);
            }
        }
    }

    /**
     * Takes the version of the file as it exists in the head commit and puts it
     * in the working directory, overwriting the version of the file that’s already
     * there if there is one. The new version of the file is not staged.
     **/
    public static void checkout_head_commit_file(String file_name) {
        String current_commit_id = get_Head_id();
        checkout_specified_commit_file(current_commit_id, file_name);
    }

    /**
     * Takes the version of the file as it exists in the commit with the given id,
     * and puts it in the working directory, overwriting the version of the file
     * that’s already there if there is one. The new version of the file is not staged.
     **/
    public static void checkout_specified_commit_file(String commit_id, String file_name) {
        Commit specified_commit = Commit.from_file(commit_id);
        Map<String, String> specified_blobs = specified_commit.get_blobs();
        String corresponding_blob_id = specified_blobs.get(file_name);
        //check if corresponding_blob exits
        if (corresponding_blob_id == null) {
            throw error("File does not exist in that commit.");
        }
        //overwrite the version of the file in the working directory
        String tracked_content = get_content_from_blob(corresponding_blob_id);
        File specified_file = join(CWD, file_name);
        writeContents(specified_file, tracked_content);
    }

    /**
     * Takes all files in the commit at the head of the given branch, and puts them
     * in the working directory, overwriting the versions of the files that are already
     * there if they exist. Also, at the end of this command, the given branch will now be
     * considered the current branch (HEAD). Any files that are tracked in the current branch
     * but are not present in the checked-out branch are deleted. The staging area is cleared,
     * unless the checked-out branch is the current branch.
     */
    public static void checkout_specified_branch(String branch_name) {
        //check if the given branch exits
        File branch_file = join(BRANCH_FOLDER, branch_name);
        if (!branch_file.exists()) {
            throw error("No such branch exists.");
        }
        //check if the current branch is HEAD branch
        if (branch_name.equals(get_Head_name())) {
            throw error("No need to checkout the current branch.");
        }
        //cheek if a working file is untracked in the current branch and would be overwritten by the checkout
        validate_CWD_untracks_curr_commit();
        //delete the current files(not directory) in working directory
        List<String> file_names = plainFilenamesIn(CWD);
        if (file_names != null) {
            for (String name_to_delete : file_names) {
                File file_to_delete = join(CWD, name_to_delete);
                restrictedDelete(file_to_delete);
            }
        }
        //overwrite each file in the working directory
        String branch_id = get_specified_branch(branch_name);
        Commit specified_commit = Commit.from_file(branch_id);
        Map<String, String> specified_blobs = specified_commit.get_blobs();
        for (Map.Entry<String, String> entry : specified_blobs.entrySet()) {
            String file_name = entry.getKey();
            checkout_specified_commit_file(branch_id, file_name);
        }
        //The staging area is cleared, unless the checked-out branch is the current branch.
        clear_staging_area();
        //update the HEAD branch
        update_Head(branch_name);
    }

    /**
     * Creates a new branch with the given name, and points it at the current head commit.
     * A branch is nothing more than a name for a reference (a SHA-1 identifier) to a commit node.
     * This command does NOT immediately switch to the newly created branch (just as in real Git).
     * Before you ever call branch, your code should be running with a default branch called “master”.
     */
    public static void make_branch(String branch_name) {
        File new_branch = join(BRANCH_FOLDER, branch_name);
        String HEAD_id = get_Head_id();
        writeContents(new_branch, HEAD_id);
        File split_point = join(GITLET_DIR , "split_point");
        writeContents(split_point , HEAD_id);
    }

    /**
     * Deletes the branch with the given name. This only means to delete the pointer
     * associated with the branch; it does not mean to delete all commits that were created
     * under the branch, or anything like that.
     */
    public static void delete_branch(String branch_name) {
        File given_branch = join(BRANCH_FOLDER, branch_name);
        //check if a branch with the given name does not exist
        if (!given_branch.exists()) {
            throw error("A branch with that name does not exist.");
        }
        //check if it's trying to remove the current branch
        if (branch_name.equals(get_Head_name())) {
            throw error("Cannot remove the current branch.");
        }
        given_branch.delete();
    }

    /**
     * Checks out all the files tracked by the given commit. Removes tracked files that are not
     * present in that commit. Also moves the current branch’s head to that commit node.
     * See the intro for an example of what happens to the head pointer after using reset.
     * The [commit id] may be abbreviated as for checkout. The staging area is cleared.
     * The command is essentially checkout of an arbitrary commit that also changes the
     * current branch head.
     */
    public static void reset(String commit_id) {
        //check if commit with the given id exist
        if (!Commit.is_commit_exist(commit_id)) {
            throw error("No commit with that id exists.");
        }
        //check if a working file is untracked in the current branch and would be overwritten by the reset
        validate_CWD_untracks_curr_commit();
        //reset the current branch check out there
        update_current_branch(commit_id);
        String HEAD_branch_name = get_Head_name();
        checkout_specified_branch(HEAD_branch_name);
    }

    private static void put_items_to_set(Set<String> given_set , Commit given_commit){
        Blobs_map current_blobs = given_commit.get_blobs();
        for (Map.Entry<String, String> entry : current_blobs.entrySet()) {
            String file_name = entry.getKey();
            given_set.add(file_name);
        }
    }

    //Merges files from the given branch into the current branch.
    public static void merge(String branch_name) {
        //check if there are staged additions or removals present
        List<String> file_names_addition = plainFilenamesIn(STAGING_ADD);
        List<String> file_names_removal = plainFilenamesIn(STAGING_REMOVAL);
        if (! file_names_removal.isEmpty() || ! file_names_addition.isEmpty()) {
            throw error("You have uncommitted changes.");
        }
        // check if a branch with the given name does not exist
        File given_branch = join(BRANCH_FOLDER, branch_name);
        if (!given_branch.exists()) {
            throw error("A branch with that name does not exist.");
        }
        //check if attempting to merge a branch with itself
        if (branch_name.equals(get_Head_name())) {
            throw error("Cannot merge a branch with itself.");
        }
        //check if an untracked file in the current commit would be overwritten or deleted by the merge
        validate_CWD_untracks_curr_commit();
        //initialize associated file names to the set
        Set<String> associated_file_names = new TreeSet<>();
        Commit current_commit = get_current_commit();
        put_items_to_set(associated_file_names , current_commit);
        String branch_id = get_specified_branch(branch_name);
        Commit given_commit = Commit.from_file(branch_id);
        put_items_to_set(associated_file_names , given_commit);
        //get the commit on split point
        String split_point_id = get_split_point();
        Commit split_point = Commit.from_file(split_point_id);
        //merge files
        //If the split point is the same commit as the given branch, then do nothing
        if (split_point_id.equals(branch_id)){
            System.out.println("Given branch is an ancestor of the current branch.");
        }//If the split point is the current branch, then the effect is to check out the given branch.
        else if (split_point_id.equals(get_Head_id())) {
            checkout_specified_branch(branch_name);
            System.out.println("Current branch fast-forwarded.");
        }else {
            Blobs_map split_point_blobs = split_point.get_blobs();
            Blobs_map current_blobs = current_commit.get_blobs();
            Blobs_map given_blobs = given_commit.get_blobs();
            Boolean encountered_conflict = false;
            for (String file_name : associated_file_names){
                String split_point_blob_id = split_point_blobs.get(file_name);
                String current_blob_id = current_blobs.get(file_name);
                String given_blob_id = given_blobs.get(file_name);
                //not in split point and HEAD branch but in given branch -> check out given branch
                if (split_point_blob_id == null && current_blob_id == null && given_blob_id != null) {
                    checkout_specified_commit_file(given_blob_id , file_name);
                    add_file(file_name);
                }
                //modified in given branch but not in HEAD branch -> given branch
                else if (!split_point_blob_id.equals(given_blob_id) && current_blob_id == null){
                    checkout_specified_commit_file(given_blob_id , file_name);
                    add_file(file_name);
                }
                //unmodified in HEAD but not present in given branch -> remove
                else if (split_point_blob_id.equals(current_blob_id) && given_blob_id == null){
                    remove_file(file_name);
                }
                //modified in HEAD and given branch in different way -> conflict
                else if (!split_point_blob_id.equals(current_blob_id) && !split_point_blob_id.equals(given_blob_id)
                        && !current_blob_id.equals(given_blob_id)) {
                    encountered_conflict = true;
                    File associated_file = join(CWD , file_name);
                    String curr_tracked_content = get_content_from_blob(current_blob_id);
                    String given_tracked_content = get_content_from_blob(given_blob_id);
                    writeContents(associated_file,
                            "<<<<<<< HEAD\n"
                                    + curr_tracked_content
                                    + "=======\n"
                                    + given_tracked_content);
                    add_file(file_name);
                }
            }
            //make a new commit as a merged commit
            make_commit(String.format("Merged %s into %s.",branch_name , get_Head_name()) , branch_id);
            //if the merge encountered a conflict, print the message Encountered a merge conflict.
            if (encountered_conflict){
                System.out.println("Encountered a merge conflict.");
            }
            //remove the split point
            remove_split_point();
        }
    }
}