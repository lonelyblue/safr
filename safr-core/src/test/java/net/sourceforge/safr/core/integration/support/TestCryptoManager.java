/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.safr.core.integration.support;

import net.sourceforge.safr.core.provider.CryptoManager;
import net.sourceforge.safr.core.spring.annotation.CryptoOperationPoint;

/**
 * A {@link CryptoManager} implementation for testing purposes. Encryption and
 * decryption operations simply revert the character sequence of a
 * {@link String}.
 * 
 * @author Martin Krasser
 */
@CryptoOperationPoint
public class TestCryptoManager implements CryptoManager {

    private CryptoHistory cryptoHistory;
    
    public TestCryptoManager() {
        cryptoHistory = new CryptoHistory();
    }
    
    public CryptoHistory getCryptoHistory() {
        return cryptoHistory;
    }
    
    public Object encrypt(Object value, Object context) {
        cryptoHistory.add(new Crypto(Crypto.Operation.ENCRYPT, value, context));
        return reverse((String)value);
    }

    public Object decrypt(Object value, Object context) {
        cryptoHistory.add(new Crypto(Crypto.Operation.DECRYPT, value, context));
        return reverse((String)value);
    }

    private String reverse(String s) {
        return new StringBuffer(s).reverse().toString();
    }
    
}
