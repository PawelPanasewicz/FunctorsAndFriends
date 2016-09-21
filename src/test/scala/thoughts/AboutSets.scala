package thoughts

class AboutSets extends ynfrastructure.Spec {


    //Disclaimer.

    //Below are just my opinionated, random thoughts from programmer's perspective,
    //based after reading some Math and Category Theory stuff.
    //They don't need to be true. But they at least show how math lectures can be understood by ~10years experienced
    //developer which finished information technology but no math.

    //Refs:
    //-(1) https://bartoszmilewski.com/2014/10/28/category-theory-for-programmers-the-preface/
    //-(2) http://matt.might.net/articles/partial-orders/
    //-(3) http://milessabin.com/blog/2011/06/09/scala-union-types-curry-howard/

    //What are sets?
    //
    //Imagine some objects, for example:
    //- letters in "hello world"
    //- all moods ever
    //- trees in forest
    //- numbers between 1 and 10
    //- weights of birds
    //- all capibaras drinking water
    //- all pure scala expressions of given types
    //- all values of type Int in scala
    //- all types in scala.lang package
    //- all values of type String
    //- all scala types generated by kind Set[_]
    //- all functions taking one argument and returning tuple (scala language context)
    //- all x that x is y%16 and y is Natural Number
    //- all objects together mentioned before
    //etc.

    //These are some objects. You can define them using words, or math expressions. Some of them you can express in programming
    //language like scala. Some of them are countable, others don't. Sometimes it's easy to tell that element is part of
    //set, sometimes it's harder or even not possible.

    //We can say that that Set are just some objects put or rather considered together.
    //In scala language sets can be modeled in many ways. For example hierarchy of types can form some kind of sets.
    //For example scala.Int can be thought as just set of all specific numbers, scala.String can be thought as just set of
    //all instances of strings. scala.Any represents all instances ever that can be made using scala language.

    //We can think that a scala type can be some kind of a set.
    //Similarly we can think that type constructors can form a set (of types or of instances of types...)
    //
    //Other way to express sets in scala is just to create an instance of some container. Elements in this container
    //can be thought as objects of some set. For example:

    val xs = Set(1,2,3) // - 1,2,3 are members of set xs.

    //This is quite limited way of defining sets but enogh to get some meaningful intuition.
    //Using containers we can not express all positive numbers for example (without any acrobatics with extending/overridings in Set trait).

    //Other way to express some set in scala is to use some recursive tools.
    //Stream for example allows to express  set (in math context) of positive numbers:
    val positiveNumbers: Stream[scala.math.BigInt] = Stream.iterate(BigInt(2))(_+2)

    //Yet another way to express such set is to use types

    sealed trait PositiveNumber
    case object First extends PositiveNumber
    case class Next(previous: PositiveNumber) extends PositiveNumber

    //We can interprete that
    First // is just 2, so it is first positive number
    Next(First) //is next positive number after 2 so it is 4
    Next(Next(First)) // is 6 and so on...

    //Let's take a look at some built in types in scala and what they are in mathematical set context.

    //One funny set which exist in programming languages is called bottom.
    //In scala it is represented by Nothing type. There is even alias for it defined in scalaz library.
    type `scalaz ⊥ is just Nothing` = scalaz.⊥ //see scalaz package object.

    //The name comes from haskell language.
    //The type Nothing has no instances. There are no instance of such type in scala.
    //Set represented by this type has no elements.
    //It's simply empty. But type and set by it's own exist.

    //What does it mean? It represents situation, when computation never terminates or exception has been thrown.
    //We can use this type to construct interesting expressions.
    //What such function can mean? I haven't found practical usage of this expression, but for sure it's possible to construct such.

    def f(a: Nothing): Unit = println("I will never execute nanana!")

    //f will be never called though it is still valid object.
    //Object 'a' must be one among all objects in Set Nothing, but since there are no objects in this set,
    //we can not call this function. Does it mean that a doesn't exist :0

    //I hope that now after introduction to nothing, nothing stays on a way of mortal developer in order to
    //understand how to define negation using types
    //...see ref(3)
    type ¬[A] = A => Nothing

    //One more tip about Nothing type.
    //It's not said loudly but every expression in scala has either declared type or Nothing (just in case when
    //computation never ends or exception occurs).
    //This is probably why Nothing is subtype of all other types.
    //Nothing is subtype of other types that means that that it is like bottom. I hope it make sense.

    //Other interesting type in scala is scala.Unit. Unit has only one instance, namely
    ()

    //One another interesting set (I mean type) is Boolean. It has two objects. Gues what they are :)

    //Ok. Now let's come back from digression what Nothing, Unit and Boolean types are.

    //At this point we should have some intuition what sets are and how to express them in scala.
    //Assuming yes it's easy to hear the question:
    //So what?

    //Now if we (software developers, not mathematicians) know what are sets, we are able to understand
    //other Math concepts. We (developers) should care about them because these concepts are widely used
    //in computer science and allow for better understanding what functional programming is, what is the category theory
    //and maybe later monads, monoids and [fancy prefix here]functors - recently such popular topics within scala community.

    //Given intuition of set it should became easier to understand (for programmers) what are
    // - Preorder Sets
    // - Partial Order Sets and
    // - Total Orders sets


    //What is Preorder Set?

    //Definition from Ref(1):
    //A binary relation ⊑ on a set X is a preorder if the relation ⊑ is both reflexive and transitive.
    //A binary relation R is reflexive if x R x for any x.
    //A binary relation R is transitive if a R b and b R c implies a R c.
    //
    //Hmm
    //
    //Hmmmmm ...
    //
    //Ok, First what is a (binary) relation?
    //Let's start with examples.
    // - Ex1. There are relations between integers (which are objects) telling of one integer is bigger other. We call this relation
    // > or >= or < or <=
    // - Ex2. There is relation between strings, for example telling if string A has more letters than string B
    // - Ex3. There is relation between types telling that type A is super type of B
    // - Ex4. Given function f: A => B. Objects from Set A and Set B are in relation where for y = f(x) and y belongs to B, x belongs to A

    //  etc
    // Ex1 and Ex2 can be defined using below piece of code, which will tell if two scala objects are in realtion or no.

    def ex1(a: Int, b: Int): Boolean = a > b
    def ex2(a: String, b: String): Boolean = a.size > b.size
    //so relation here is a way of telling if two objects from some particular set of objects which is taken into consideration
    //... if two objects are in relation or no. This is why result of above functions returns Boolean

    //Ex3 can be expressed differently:
    def ex3[A <: B, B]() = ()
    //In contrast to ex1, ex2 here we are checking during compilation time if two objects are in the relation.
    //Well, this way is just another concept of "returning" boolean which can be interpreted if A and B are in relation type to super type.
    //The relation from ex3 can be expressed as well as
    //import scala.Predef._
    def ex3V2[A, B](a: A <:< B) = ()
    //I still don't fully understand how does this expression work
    //but for sure these ~25k characters should definitely shed some more light on it - http://scalabound.org/?p=323
    //ad Ex4.
    //given some function Int => String
    def f(i: Int): String = (i+100).toString
    //we can say that somee Int and Strnig are in relation where for given pair(x: Int, y: String)
    val (x,y): (Int, String) = (100, "200")
    //this is true:
    f(x) == y //is it true?
    //(In this case relation can be described as well using function between two types)
    //... but there are relations, which can not be expressed using funcions, because function for given X can have only one Y and relations not...

    //Taking these scraps together man could understand that there is some considered Set of some objects (o1, o2, ...)
    //and taking any two objects freom this set we can state
    //that they are in relation or not (true, false, compiling, not compiling).
    //whatever the relation under the hood is defined (or should I say implemented?)
    //Sometimes it is easy to define relation graphically.
    //Given set of 3 objects (A,B,C) x marks in below table will denote if pair of objects are in relation:
    //
    //         A  B  C
    //      A  x
    //      B     x
    //      C        x
    //
    //In other words in relation are only (A,A), (B,B), (C,C)

    //Given the intuition of what is the binary relation now should be easier to take different types of relations in.
    //For example reflexive relation is one which holds only for the same objects.
    //That mean that if we consider some set say all integers and relation is defined as function
    def R1(a: Int, b: Int) = a == b
    //we can say that this relation is reflexive because function R1 returns true only for the same arguments.
    //similarly we can say that given relation is transitive (if a R b and b R c implies a R c.)

    //Ok let's devlope some set and relation which is not transitive:
    sealed trait SA
    case object SA_B extends SA
    case object SA_C extends SA
    case object SA_D extends SA
    //Set SA has just two object SAB and SAC

    def SARel1(a: SA, b: SA): Boolean = false //hmm, no relation. Wired thing. lets leave it
    def SARel2(a: SA, b: SA): Boolean =
      a == SA_B && b == SA_C ||
      a == SA_C && b == SA_D
    //SARel2 returns true only in one case.
    SARel2(SA_B, SA_B) mustBe false //that means that this is not reflexive, because for any input relation must hold
    //let's check if relation is transitive?
    SARel2(SA_B, SA_C) mustBe true
    SARel2(SA_C, SA_D) mustBe true
    SARel2(SA_B, SA_D) mustBe false   //nope. This relation is not transitive nor reflexive.
  //Done! We successfully defined relation which doesn't define Preorder set.

  //Let's now define relation which is both reflexive and transitive - thanks to that we will define Preorder Set.
  //To do this les's condider set of all strings, and relation would be the equality of wowels between instances of strings

  //two strings are in relation, if they containt the same amount of wowels characters
  val wowels = List('a', 'o', 'u', 'e', 'i', 'y')
  def howManyWowels(s: String) = s.count(c => wowels.contains(c.toLower))
  def SARel3(a: String, b: String) = howManyWowels(a) == howManyWowels(b)

  //this should be both transitive and reflexive. In order to prove it we must test for all possible inputs
  "SARel3 is transitive" in {
    //in fact this test doesn't proove it but at least it test some scenarios
    //which are comparing objcets which are in raltion and are not
    //this should be interpreted reather as sanity checks
    for {
      a <- List("hello world", "attack wurms", "ballon gwork", "some other string that is not in relation", "")
      b <- List("hello world", "attack wurms", "ballon gwork", "some other string that is not in relation", "")
      c <- List("hello world", "attack wurms", "ballon gwork", "some other string that is not in relation", "")
    } {
      if (SARel3(a, b) && SARel3(b, c)) SARel3(a, c) mustBe true withClue s"$a $b $c"
    }
  }

  //TODO finish story about Partial Order Set and Total Set.
  //TODO: Sorting
}
