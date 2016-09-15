/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.codehaus.groovy.tools;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;

public class GroovyClass
{
    public static final GroovyClass[] EMPTY_ARRAY = new GroovyClass[ 0 ];

    private String name;
    private byte[] bytes;
    // GRECLIPSE add
    private ClassNode classNode;
    private SourceUnit source;

    public GroovyClass(String name, byte[] bytes, ClassNode classNode, SourceUnit source)
    {
        this.name  = name;
        this.bytes = bytes;
        this.classNode = classNode;
        this.source = source;
    }

    public ClassNode getClassNode() {
        return classNode;
    }

    public SourceUnit getSourceUnit() {
        return source;
    }
    // GRECLIPSE end

    public String getName()
    {
        return this.name;
    }

    public byte[] getBytes()
    {
        return this.bytes;
    }
}

