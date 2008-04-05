package net.sourceforge.safr.core.annotation.field;

import java.lang.reflect.Field;

import net.sourceforge.safr.core.annotation.Encrypt;
import net.sourceforge.safr.core.annotation.info.EncryptAnnotationInfo;
import net.sourceforge.safr.core.attribute.EncryptAttribute;
import net.sourceforge.safr.core.attribute.field.FieldAttributeCollector;
import net.sourceforge.safr.core.attribute.field.FieldAttributeContainer;

public class FieldAnnotationCollector extends FieldAttributeCollector {

    @Override
    public boolean visit(Field f) {
        FieldAttributeContainer container = getFieldAttributeContainer();
        container.clear();
        container.setFieldEncryptAttribute(getFieldEncryptAttribute(f));
        return container.isAnyFieldAttributeDefined();
    }

    private static EncryptAttribute getFieldEncryptAttribute(Field f) {
        return createEncryptAttribute(f.getAnnotation(Encrypt.class));
    }

    private static EncryptAttribute createEncryptAttribute(Encrypt annotation) {
        if (annotation == null) {
            return null;
        }
        return new EncryptAnnotationInfo(annotation);
    }
    
}
