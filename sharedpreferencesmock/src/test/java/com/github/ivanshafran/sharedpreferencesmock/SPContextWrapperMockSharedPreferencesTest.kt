package com.github.ivanshafran.sharedpreferencesmock

import android.content.SharedPreferences
import com.github.ivanshafran.sharedpreferencesmock.internal.ContextMock
import com.github.ivanshafran.sharedpreferencesmock.internal.SPContextWrapperMock
import com.github.ivanshafran.sharedpreferencesmock.internal.SharedPreferencesFactory
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SPContextWrapperMockSharedPreferencesTest : Spek({

    describe("context mock") {
        val context by memoized {
            SPContextWrapperMock(
                    ContextMock(),
                    SharedPreferencesFactory(false)
            )
        }

        context("on first preferences request") {

            it("should return non null value of SharedPreferences") {
                assertNotNull(context.getSharedPreferences("", 0))
            }
        }

        context("on several request with the same name") {

            it("should return the same instance") {
                assertTrue { context.getSharedPreferences("", 0) === context.getSharedPreferences("", 0) }
            }
        }

        context("on several request with different names") {

            it("should return different instances") {
                assertTrue { context.getSharedPreferences("1", 0) !== context.getSharedPreferences("2", 0) }
            }
        }

        context("after preferences is deleted") {

            context("on the same preference request") {
                lateinit var first: SharedPreferences
                lateinit var second: SharedPreferences

                beforeEachTest {
                    first = context.getSharedPreferences("", 0)
                    context.deleteSharedPreferences("")
                    second = context.getSharedPreferences("", 0)
                }

                it("should return new instance") {
                    assertTrue { first !== second }
                }
            }
        }
    }

})
