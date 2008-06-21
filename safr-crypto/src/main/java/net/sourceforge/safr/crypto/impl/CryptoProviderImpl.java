package net.sourceforge.safr.crypto.impl;

import java.util.Map;

import net.sourceforge.safr.core.provider.CryptoProvider;
import net.sourceforge.safr.core.spring.annotation.CryptographicServiceProvider;

@CryptographicServiceProvider
public class CryptoProviderImpl implements CryptoProvider {

    public Object decrypt(Object value, Object context, Map<String, String> hints) {
        throw new UnsupportedOperationException("not implemented");
    }

    public Object encrypt(Object value, Object context, Map<String, String> hints) {
        throw new UnsupportedOperationException("not implemented");
    }

}
