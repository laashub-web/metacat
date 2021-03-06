/*
 *  Copyright 2018 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.netflix.metacat.common.server.connectors.model

import com.netflix.metacat.common.type.TypeRegistry
import com.netflix.metacat.common.type.TypeSignature
import spock.lang.Shared;
import spock.lang.Specification;

/**
 * Specifications for FieldInfoSpec serialization/deserialization.
 *
 * @author amajumdar
 * @since 1.2.0
 */
class FieldInfoSpec extends Specification {
    @Shared
    def file

    def setupSpec() {
        file = new File('fieldInfo.txt')
    }

    def cleanupSpec() {
        file.delete()
    }
    def test() {
        given:
        def type = signature == null ? null : TypeRegistry.getTypeRegistry().getType(TypeSignature.parseTypeSignature(signature))
        def field = new FieldInfo(comment:'hi', name:'name',partitionKey: true, isNullable: true, size: 10, defaultValue: '10', type: type)
        file.withObjectOutputStream { oos -> oos.writeObject(field) }
        def savedField = (FieldInfo) file.withObjectInputStream { ois -> ois.readObject()}
        expect:
        savedField.type == type
        where:
        signature << [
        null,
        'tinyint',
        'smallint',
        'int',
        'bigint',
        'float',
        'double',
        'decimal(4,2)',
        'array<decimal(4,2)>',
        'timestamp',
        'date',
        'string',
        'varchar(10)',
        'char(10)',
        'boolean',
        'binary',
        'array<bigint>',
        'array<boolean>',
        'array<double>',
        'array<bigint>',
        'array<string>',
        'array<map<bigint,bigint>>',
        'array<map<bigint,string>>',
        'array<map<string,bigint>>',
        'array<map<string,string>>',
        'array<struct<field1:bigint,field2:bigint>>',
        'array<struct<field1:bigint,field2:string>>',
        'array<struct<field1:string,field2:bigint>>',
        'array<struct<field1:string,field2:string>>',
        'map<boolean,boolean>',
        'map<boolean,string>',
        'map<bigint,bigint>',
        'map<string,double>',
        'map<string,bigint>',
        'map<string,string>',
        'map<string,struct<field1:array<bigint>>>',
        'struct<field1:bigint,field2:bigint,field3:bigint>',
        'struct<field1:bigint,field2:string,field3:double>',
        'struct<field1:bigint,field2:string,field3:string>',
        'struct<field1:string,field2:bigint,field3:bigint>',
        'struct<field1:string,field2:string,field3:bigint>',
        'struct<field1:string,field2:string,field3:string>']
    }
}
