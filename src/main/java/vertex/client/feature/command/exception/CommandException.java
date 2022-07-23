

package vertex.client.feature.command.exception;

public class CommandException extends Exception {
    final String potentialFix;

    public CommandException(String cause, String potentialFix) {
        super(cause);
        this.potentialFix = potentialFix;
    }

    public String getPotentialFix() {
        return potentialFix;
    }
}
