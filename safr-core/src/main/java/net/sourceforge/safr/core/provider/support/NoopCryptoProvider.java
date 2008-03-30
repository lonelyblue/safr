package net.sourceforge.safr.core.provider.support;

import net.sourceforge.safr.core.provider.CryptoProvider;

public class NoopCryptoProvider implements CryptoProvider {

    public Object encrypt(Object value, Object context) {
        return value;
    }

    public Object decrypt(Object value, Object context) {
        return value;
    }

}
