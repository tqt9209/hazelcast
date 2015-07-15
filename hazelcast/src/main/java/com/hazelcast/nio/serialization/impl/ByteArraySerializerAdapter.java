/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.nio.serialization.impl;


import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.ByteArraySerializer;
import com.hazelcast.nio.serialization.Serializer;

import java.io.IOException;

class ByteArraySerializerAdapter implements SerializerAdapter {

    protected final ByteArraySerializer serializer;

    public ByteArraySerializerAdapter(ByteArraySerializer serializer) {
        this.serializer = serializer;
    }

    @SuppressWarnings("unchecked")
    public void write(ObjectDataOutput out, Object object) throws IOException {
        byte[] bytes = serializer.write(object);
        out.writeByteArray(bytes);
    }

    public Object read(ObjectDataInput in) throws IOException {
        byte[] bytes = in.readByteArray();
        if (bytes == null) {
            return null;
        }
        return serializer.read(bytes);
    }

    public int getTypeId() {
        return serializer.getTypeId();
    }

    public void destroy() {
        serializer.destroy();
    }

    @Override
    public Serializer getImpl() {
        return serializer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SerializerAdapter{");
        sb.append("serializer=").append(serializer);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ByteArraySerializerAdapter that = (ByteArraySerializerAdapter) o;

        if (serializer != null ? !serializer.equals(that.serializer) : that.serializer != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return serializer != null ? serializer.hashCode() : 0;
    }
}