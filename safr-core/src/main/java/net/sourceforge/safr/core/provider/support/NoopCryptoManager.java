package net.sourceforge.safr.core.provider.support;

import net.sourceforge.safr.core.provider.CryptoManager;

public class NoopCryptoManager implements CryptoManager {

    public Object encrypt(Object value, Object context) {
        return value;
    }

    public Object decrypt(Object value, Object context) {
        return value;
    }

}
