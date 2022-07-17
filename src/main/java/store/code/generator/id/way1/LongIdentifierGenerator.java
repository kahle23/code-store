package store.code.generator.id.way1;

/**
 * Long identifier generator.
 * @author Kahle
 */
public interface LongIdentifierGenerator extends IdentifierGenerator {

    /**
     * Randomly generate the next long identifier.
     * @return Next long identifier
     */
    Long nextLongIdentifier();

}
