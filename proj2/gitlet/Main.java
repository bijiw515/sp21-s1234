package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        String command = args[0];
        switch(command) {
            case "init":
                //java gitlet.Main init
                validateNumArgs("init", args, 1);
                Repository.init();
                break;
            case "add":
                //java gitlet.Main add [file name]
                validateNumArgs("add", args, 2);
                String file_addition = args[1];
                Repository.add_file(file_addition);
                break;
            case "commit":
                //java gitlet.Main commit [message]
                validateNumArgs("commit" , args , 2);
                String commit_message = args[1];
                Repository.make_commit(commit_message);
                break;
            case "rm":
                //java gitlet.Main rm [file name]
                validateNumArgs("rm" , args , 2);
                String file_removal = args[1];
                Repository.remove_file(file_removal);
                break;
            case "log":
                //java gitlet.Main log
                validateNumArgs("log" , args , 1);
                Repository.log();
                break;
            case "global-log":
                //java gitlet.Main global-log
                validateNumArgs("global-log" , args ,1);
                Repository.global_log();
                break;
            case "find":
                //java gitlet.Main find [commit message]
                validateNumArgs("find" , args , 2);
                String message_find = args[1];
                Repository.find(message_find);
                break;
            case "status":
                //java gitlet.Main status
                validateNumArgs("status" , args , 1);
                Repository.print_status();
            case "checkout":
                String operation = args[1];
                //java gitlet.Main checkout -- [file name]
                if (operation.equals("--")){
                    validateNumArgs("checkout" , args , 3);
                    String specified_file_name = args[2];
                    Repository.checkout_head_commit_file(specified_file_name);
                }
                //java gitlet.Main checkout [commit id] -- [file name]
                else if (args.length == 4){
                    String commit_id = args[1];
                    String specified_file_name = args[3];
                    Repository.checkout_specified_commit_file(commit_id , specified_file_name);
                }
                //java gitlet.Main checkout [branch name]
                else {
                    validateNumArgs("checkout" , args , 2);
                    String branch_id = args[1];
                    Repository.checkout_specified_commit(branch_id);
                }
                break;
            case "branch":
                //java gitlet.Main branch [branch name]
                validateNumArgs("branch" , args , 2);
                String branch_id = args[1];
                Repository.make_branch(branch_id);
                break;
        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param cmd Name of command you are validating
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            if (args[0].equals("commit")){
                throw error("Please enter a commit message.");
            }
            else {
                throw error(String.format("Invalid number of arguments for: %s.", cmd));
            }
        }
    }
}
