// "Add remaining branches" "true"
// WITH_STDLIB

sealed class A
class B : A()

fun test(a: A) {
    val r = <caret>when (a) {

        // comment

    }
}