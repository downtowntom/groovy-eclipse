/*
 * Copyright 2009-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.eclipse.codebrowsing.tests

import org.junit.Test

final class CodeSelectFieldsTests extends BrowsingTestSuite {

    @Test
    void testCodeSelectFieldInClass() {
        assertCodeSelect(['class Foo {\n def x = 9\ndef y() { x++ }\n }'], 'x')
    }

    @Test
    void testCodeSelectFieldInOtherClass() {
        assertCodeSelect(['class Foo { def x = 9\n }', 'class Bar { def y() { new Foo().x++\n }\n }'], 'x')
    }

    @Test
    void testCodeSelectFieldInSuperClass() {
        assertCodeSelect(['class Foo { def x = 9\n }', 'class Bar extends Foo { def y() { x++\n }\n }'], 'x')
    }

    @Test
    void testCodeSelectStaticFieldInClass() {
        assertCodeSelect(['class Foo {\n static def x = 9\ndef y() { Foo.x++ }\n }'], 'x')
    }

    @Test
    void testCodeSelectStaticFieldInOtherClass() {
        assertCodeSelect(['class Foo { static def x = 9\n }', 'class Bar { def y() { Foo.x++\n }\n }'], 'x')
    }

    @Test
    void testCodeSelectLazyFieldInClass() {
        assertCodeSelect(['class Foo {\n  @Lazy def x = 9\n}'], 'x')
    }

    @Test
    void testCodeSelectLoggerFieldInClass() {
        // field added by @Log transform is not fully selectable
        assertCodeSelect(['''\
            @groovy.util.logging.Log
            class Foo {
              String str
              def meth() {
                log.info "$str msg"
              }
            }'''.stripIndent()
        ], 'log', 'Foo'); // TODO: Want this to be 'log', but field is not in Java model.
    }

    @Test
    void testCodeSelectInClosure() {
        assertCodeSelect(['def x = {\nt -> print t\n}\nx("hello")'], 't')
    }

    @Test
    void testCodeSelectInClosure2Params() {
        assertCodeSelect(['def x = {\ns, t -> print t\n}\nx("hello")'], 't')
    }

    @Test
    void testCodeSelectLocalVarInClosure() {
        assertCodeSelect(['def y = 9\ndef x = {\nt -> print y\n}'], 'y')
    }

    @Test
    void testCodeSelectFieldInClosure() {
        assertCodeSelect(['class X { \n def y=9\n } \ndef x = {\nt -> print new X().y\n}'], 'y')
    }

    @Test
    void testCodeSelectFieldFromSuperInClosure() {
        assertCodeSelect(['class X { \n def y=9\n } \nclass Y extends X { }\ndef x = {\nt -> print new Y().y\n}'], 'y')
    }

    @Test
    void testCodeSelectStaticFieldInClosure() {
        assertCodeSelect(['class X { \n static def y=9\n \ndef z() {\ndef x = {\nt -> print X.y\n}\n}\n}'], 'y')
    }

    @Test
    void testCodeSelectStaticFieldFromOtherInClosure() {
        assertCodeSelect(['class X { \n static def y=9\n } \ndef x = {\nt -> print X.y\n}'], 'y')
    }

    @Test
    void testCodeSelectInFieldInitializer() {
        assertCodeSelect(['class X { \n def y= { z() }\ndef z() { } }'], 'z')
    }

    @Test
    void testCodeSelectInStaticFieldInitializer() {
        assertCodeSelect(['class X { \n static y= { z() }\nstatic z() { } }'], 'z')
    }

    // GRECLIPSE-516
    @Test
    void testCodeSelectOfGeneratedGetter() {
        assertCodeSelect(['class C { \n int num\ndef foo() {\n getNum() } }'], 'getNum', 'num')
    }

    // GRECLIPSE-516
    @Test
    void testCodeSelectOfGeneratedSetter() {
        assertCodeSelect(['class C { \n int num\ndef foo() {\n setNum() } }'], 'setNum', 'num')
    }

    @Test
    void testCodeSelectInsideGString1() {
        assertCodeSelect(['def foo\n"${foo}"'], 'foo')
    }

    @Test
    void testCodeSelectInsideGString2() {
        assertCodeSelect(['def foo\n"${foo.toString()}"'], 'foo')
    }

    @Test
    void testCodeSelectInsideGString3() {
        assertCodeSelect(['def foo\n"${foo.toString()}"'], 'toString')
    }

    @Test
    void testCodeSelectInsideGString4() {
        assertCodeSelect(['def foo\n"${foo}"'], 'o', 'foo')
    }

    @Test
    void testCodeSelectInsideGString5() {
        assertCodeSelect(['def foo\n"${toString()}"'], 'toString')
    }
}