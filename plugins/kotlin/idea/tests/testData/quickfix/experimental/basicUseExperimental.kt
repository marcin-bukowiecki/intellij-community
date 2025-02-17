// "Add '@OptIn(MyExperimentalAPI::class)' annotation to 'bar'" "true"
// PRIORITY: HIGH
// COMPILER_ARGUMENTS: -Xopt-in=kotlin.RequiresOptIn
// WITH_STDLIB

package a.b

@RequiresOptIn
@Target(AnnotationTarget.CLASS)
annotation class MyExperimentalAPI

@MyExperimentalAPI
class Some {
    fun foo() {}
}

class Bar {
    fun bar() {
        Some().foo<caret>()
    }
}
