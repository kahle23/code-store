package store.code.data.json.support.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UniversalListDeserializer.
 * @author Kahle
 */
public class UniversalListDeserializer extends JsonDeserializer<List<?>> implements ContextualDeserializer {
    private JavaType elementType;
    private JavaType fieldType;

    public UniversalListDeserializer(JavaType fieldType, JavaType elementType) {
        this.elementType = elementType;
        this.fieldType = fieldType;
    }

    public UniversalListDeserializer() {

    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ct, BeanProperty bp) {
        JavaType fieldType = ct.getContextualType() != null
                ? ct.getContextualType() : bp.getType(), elementType;
        elementType = fieldType.getContentType();
        if (elementType == null) {
            elementType = TypeFactory.unknownType();
        }
        return new UniversalListDeserializer(fieldType, elementType);
    }

    @Override
    public List<?> deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonToken token = p.currentToken(); Object value;
        if (JsonToken.VALUE_NULL.equals(token)) { return null; }
        //
        List<Object> result = new ArrayList<Object>();
        if (JsonToken.START_ARRAY.equals(token)) {
            while (!JsonToken.END_ARRAY.equals(p.nextToken())) {
                result.add(context.readValue(p, elementType));
            }
            return result;
        } else {
            if ((value = context.readValue(p, elementType)) != null) {
                result.add(value);
            }
            return result;
        }
    }
}
