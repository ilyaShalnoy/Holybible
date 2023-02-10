package com.example.notes.holybible

import com.example.notes.holybible.core.Abstract
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import java.lang.Exception

class AbstractTest {

    @Test
    fun test_success() {
        val dataObject = TestDataObject.Success("a", "b")
        val domainObject = dataObject.map(DataToDomainMapper.Base())
        assertTrue(domainObject is TestDomainObject.Success)
    }

    @Test
    fun test_fail() {
        val dataObject = TestDataObject.Fail(IOException())
        val domainObject = dataObject.map(DataToDomainMapper.Base())
        assertTrue(domainObject is TestDomainObject.Fail)
    }

    private sealed class TestDataObject : Abstract.Object<TestDomainObject, DataToDomainMapper>() {
        abstract override fun map(mapper: DataToDomainMapper): TestDomainObject

        class Success(
            private val textOne: String,
            private val textTwo: String
        ) : TestDataObject() {
            override fun map(mapper: DataToDomainMapper): TestDomainObject {
                return mapper.map(textOne, textTwo)
            }

        }

        class Fail(private val exception: Exception) : TestDataObject() {
            override fun map(mapper: DataToDomainMapper): TestDomainObject {
                return mapper.map(exception)
            }

        }

    }
}

private interface DataToDomainMapper : Abstract.Mapper {

    fun map(textOne: String, textTwo: String): TestDomainObject

    fun map(exception: Exception): TestDomainObject

    class Base : DataToDomainMapper {
        override fun map(textOne: String, textTwo: String): TestDomainObject {
            return TestDomainObject.Success("$textOne $textTwo")
        }

        override fun map(exception: Exception): TestDomainObject {
            return TestDomainObject.Fail()
        }

    }
}

private sealed class TestDomainObject : Abstract.Object<UiObject, DomainToUiMapper>() {

    class Success(private val textCombined: String) : TestDomainObject() {
        override fun map(mapper: DomainToUiMapper): UiObject {
            TODO("not done yet")
        }
    }

    class Fail : TestDomainObject() {
        override fun map(mapper: DomainToUiMapper): UiObject {
            TODO("Not yet implemented")
        }
    }
}


private interface DomainToUiMapper : Abstract.Mapper

private sealed class UiObject: Abstract.Object<Unit, Abstract.Mapper.Empty>()