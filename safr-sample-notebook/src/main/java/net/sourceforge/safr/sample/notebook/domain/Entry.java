/*
 * Copyright 2006-2008 InterComponentWare AG.
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
package net.sourceforge.safr.sample.notebook.domain;

import java.util.UUID;

/**
 * @author Martin Krasser
 */
public class Entry {

    private String id;
    
    private String text;

	public Entry() {
	    this(null);
	}

	public Entry(String text) {
		this(UUID.randomUUID().toString(), text);
	}

	public Entry(String id, String text) {
		this.id = id;
	    this.text = text;
	}

	public String getId() {
        return id;
    }

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry e = (Entry)obj;
        return id.equals(e.id); 
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
